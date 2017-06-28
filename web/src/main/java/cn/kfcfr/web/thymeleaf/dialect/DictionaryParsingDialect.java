package cn.kfcfr.web.thymeleaf.dialect;

import cn.kfcfr.web.thymeleaf.processor.DictionaryParsingTagProcessor;
import cn.kfcfr.web.thymeleaf.processor.IDictionaryParsingHandler;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class DictionaryParsingDialect extends AbstractProcessorDialect {
    protected DictionaryParsingDialect() {
        super("Dictionary Parsing Dialect", "txt", 1000);
    }

    private IDictionaryParsingHandler dictionaryParsingHandler;

    public IDictionaryParsingHandler getDictionaryParsingHandler() {
        return dictionaryParsingHandler;
    }

    public void setDictionaryParsingHandler(IDictionaryParsingHandler dictionaryParsingHandler) {
        this.dictionaryParsingHandler = dictionaryParsingHandler;
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new DictionaryParsingTagProcessor(dialectPrefix, this.dictionaryParsingHandler));
        return processors;
    }
}
