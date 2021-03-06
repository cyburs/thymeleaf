/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.standard.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.Validate;



/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.1
 *
 */
public final class VariableExpression extends SimpleExpression {
    
    private static final Logger logger = LoggerFactory.getLogger(VariableExpression.class);
    

    private static final long serialVersionUID = -4911752782987240708L;
    

    static final char SELECTOR = '$';
    
    
    private static final Pattern VAR_PATTERN = 
        Pattern.compile("^\\s*\\$\\{(.+?)\\}\\s*$", Pattern.DOTALL);
    
    static final Expression NULL_VALUE = VariableExpression.parseVariableExpression("${null}");


    
    
    private final String expression;
    private final boolean convertToString;
    
    
    
    public VariableExpression(final String expression) {
        this(expression, false);
    }


    /**
     * 
     * @param expression expression
     * @param convertToString convertToString
     * @since 2.1.0
     */
    public VariableExpression(final String expression, final boolean convertToString) {
        super();
        Validate.notNull(expression, "Expression cannot be null");
        this.expression = expression;
        this.convertToString = convertToString;
    }



    public String getExpression() {
        return this.expression;
    }


    /**
     * 
     * @return the result
     * @since 2.1.0
     */
    public boolean getConvertToString() {
        return this.convertToString;
    }


    
    @Override
    public String getStringRepresentation() {
        return String.valueOf(SELECTOR) + 
               String.valueOf(SimpleExpression.EXPRESSION_START_CHAR) +
               (this.convertToString? String.valueOf(SimpleExpression.EXPRESSION_START_CHAR) : "") +
               this.expression +
               (this.convertToString? String.valueOf(SimpleExpression.EXPRESSION_END_CHAR) : "") +
               String.valueOf(SimpleExpression.EXPRESSION_END_CHAR);
    }
    
    
    
    static VariableExpression parseVariableExpression(final String input) {
        final Matcher matcher = VAR_PATTERN.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        final String expression = matcher.group(1);
        final int expressionLen = expression.length();
        if (expressionLen > 2 &&
                expression.charAt(0) == SimpleExpression.EXPRESSION_START_CHAR &&
                expression.charAt(expressionLen - 1) == SimpleExpression.EXPRESSION_END_CHAR) {
            // Double brackets = enable to-String conversion
            return new VariableExpression(expression.substring(1, expressionLen - 1), true);
        }
        return new VariableExpression(expression, false);
    }
    

    
    
    
    
    
    static Object executeVariableExpression(
            final IExpressionContext context,
            final VariableExpression expression, final IStandardVariableExpressionEvaluator expressionEvaluator,
            final StandardExpressionExecutionContext expContext) {

        if (logger.isTraceEnabled()) {
            logger.trace("[THYMELEAF][{}] Evaluating variable expression: \"{}\"", TemplateEngine.threadIndex(), expression.getStringRepresentation());
        }
        
        final String exp = expression.getExpression();
        if (exp == null) {
            throw new TemplateProcessingException(
                    "Variable expression is null, which is not allowed");
        }

        final StandardExpressionExecutionContext evalExpContext =
            (expression.getConvertToString()? expContext.withTypeConversion() : expContext.withoutTypeConversion());

        return expressionEvaluator.evaluate(context, exp, evalExpContext, false);

    }
    
    
    
}
