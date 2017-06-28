package cn.kfcfr.web.thymeleaf.processor;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.HTMLAttributeName;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class DictionaryParsingTagProcessor extends AbstractAttributeTagProcessor {
    private static final String ATTR_NAME = "kvparse";
    private static final int PRECEDENCE = 10000;
    private IDictionaryParsingHandler dictionaryParsingHandler;

    public DictionaryParsingTagProcessor(String dialectPrefix, IDictionaryParsingHandler dictionaryParsingHandler) {
        //elementName - No tag name: match any tag name
        //prefixElementName - No prefix to be applied to tag name
        //prefixAttributeName - Apply dialect prefix to attribute name
        //removeAttribute - Remove the matched attribute afterwards
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
        this.dictionaryParsingHandler = dictionaryParsingHandler;
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler iElementTagStructureHandler) {
/*
         * In order to evaluate the attribute value as a Thymeleaf Standard Expression,
         * we first obtain the parser, then use it for parsing the attribute value into
         * an expression object, and finally execute this expression object.
         */
        final IEngineConfiguration configuration = iTemplateContext.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        IStandardExpression expression;
        HashMap<String, Object> keymap = new HashMap<>();

        if (iProcessableElementTag != null && attributeName != null) {
            String h5attrname = ((HTMLAttributeName) attributeName).getCompleteHTML5AttributeName() + "-";
            for (IAttribute attribute : iProcessableElementTag.getAllAttributes()) {
                if (attribute.getAttributeCompleteName().toLowerCase().indexOf(h5attrname) > -1) {
                    expression = parser.parseExpression(iTemplateContext, attribute.getValue());
                    Object v = expression.execute(iTemplateContext);
                    keymap.put(attribute.getAttributeCompleteName().replace(h5attrname, ""), v);
                }
            }
        }
        expression = parser.parseExpression(iTemplateContext, attributeValue);
        Object key = expression.execute(iTemplateContext);

        //由具体的业务类处理
        String rst = "";
        if (dictionaryParsingHandler != null) rst = dictionaryParsingHandler.getTextByKey(key, keymap);

        /*
         * Set the salutation as the body of the tag, HTML-escaped and
         * non-processable (hence the 'false' argument)
         */
        iElementTagStructureHandler.setBody(rst, false);
    }
}
