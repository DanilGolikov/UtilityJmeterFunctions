package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import static com.xmeter.utils.customFunctionUtils.randomFunc;


public class randomStringLiteral extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_randomStringLiteral"; //function name
    static {

        desc.add("List string literals (use | as separator)");
        desc.add("Use a separator (Optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String separatorLiterals = "|";

        String separatorLiterals_input = ((CompoundVariable) values[1]).execute().trim();
        if (!separatorLiterals_input.isEmpty())
            separatorLiterals = separatorLiterals_input;

        String[] literalsInput = (((CompoundVariable) values[0]).execute().trim()).split(Pattern.quote(separatorLiterals));

        String result = literalsInput[randomFunc(0, literalsInput.length-1)];

        String inputVar = ((CompoundVariable) values[2]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
        return result;
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) {
        values = parameters.toArray();
    }
}