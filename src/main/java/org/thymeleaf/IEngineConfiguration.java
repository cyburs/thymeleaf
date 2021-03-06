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
package org.thymeleaf;

import java.util.Map;
import java.util.Set;

import org.thymeleaf.cache.ICacheManager;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.engine.AttributeDefinitions;
import org.thymeleaf.engine.ElementDefinitions;
import org.thymeleaf.engine.TemplateManager;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.postprocessor.IPostProcessor;
import org.thymeleaf.preprocessor.IPreProcessor;
import org.thymeleaf.processor.cdatasection.ICDATASectionProcessor;
import org.thymeleaf.processor.comment.ICommentProcessor;
import org.thymeleaf.processor.doctype.IDocTypeProcessor;
import org.thymeleaf.processor.element.IElementProcessor;
import org.thymeleaf.processor.processinginstruction.IProcessingInstructionProcessor;
import org.thymeleaf.processor.templateboundaries.ITemplateBoundariesProcessor;
import org.thymeleaf.processor.text.ITextProcessor;
import org.thymeleaf.processor.xmldeclaration.IXMLDeclarationProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.text.ITextRepository;

/**
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 * 
 */
public interface IEngineConfiguration {

    public Set<ITemplateResolver> getTemplateResolvers();
    public Set<IMessageResolver> getMessageResolvers();

    public ICacheManager getCacheManager();

    public Set<DialectConfiguration> getDialectConfigurations();
    public Set<IDialect> getDialects();
    public boolean isStandardDialectPresent();
    public String getStandardDialectPrefix();

    public ITextRepository getTextRepository();

    public ElementDefinitions getElementDefinitions();
    public AttributeDefinitions getAttributeDefinitions();

    public Set<ITemplateBoundariesProcessor> getTemplateBoundariesProcessors(final TemplateMode templateMode);
    public Set<ICDATASectionProcessor> getCDATASectionProcessors(final TemplateMode templateMode);
    public Set<ICommentProcessor> getCommentProcessors(final TemplateMode templateMode);
    public Set<IDocTypeProcessor> getDocTypeProcessors(final TemplateMode templateMode);
    public Set<IElementProcessor> getElementProcessors(final TemplateMode templateMode);
    public Set<ITextProcessor> getTextProcessors(final TemplateMode templateMode);
    public Set<IProcessingInstructionProcessor> getProcessingInstructionProcessors(final TemplateMode templateMode);
    public Set<IXMLDeclarationProcessor> getXMLDeclarationProcessors(final TemplateMode templateMode);

    public Set<IPreProcessor> getPreProcessors(final TemplateMode templateMode);
    public Set<IPostProcessor> getPostProcessors(final TemplateMode templateMode);

    public Map<String,Object> getExecutionAttributes();

    public IExpressionObjectFactory getExpressionObjectFactory();

    public TemplateManager getTemplateManager();

    public IModelFactory getModelFactory(final TemplateMode templateMode);

}
