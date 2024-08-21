package dg.jmeter.plugins.functions.func;

import java.util.*;

import dg.jmeter.plugins.functions.utils.customFunctionUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import static dg.jmeter.plugins.functions.utils.customFunctionUtils.alignmentStr;
import static dg.jmeter.plugins.functions.utils.customFunctionUtils.randomFunc;


public class generatePhoneNumber extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter
    private static final String MyFunctionName = "__c_generatePhoneNumber"; //function name
    static {
        desc.add("Country code(s) (use | as separator)");
        desc.add("Format string for PHONE (use 'x' for numbers) (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }



    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String[] countryCode = ((CompoundVariable) values[0]).execute().trim().split("\\|");
        if (Objects.equals(countryCode[0], ""))
            throw new InputMismatchException("Impossible to generate phone number without a country code");

        String resultFormatString = ((CompoundVariable) values[1]).execute();
        if (!resultFormatString.isEmpty()) {
            resultFormatString = resultFormatString.replace("%", "%%");
            for (int i = 0; i < 10; i++)
                resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%s");
            resultFormatString = resultFormatString.replace("\\x", "x");
        }

        String countryCodeStr = countryCode[customFunctionUtils.randomFunc(0, countryCode.length-1)];
        String otherNumbers = customFunctionUtils.alignmentStr(customFunctionUtils.randomFunc(0, Long.parseLong("9999999999")+1), 10);
        String result = countryCodeStr + otherNumbers;

        if (!resultFormatString.isEmpty())
            result = countryCodeStr +
                    String.format(resultFormatString, otherNumbers.charAt(0),
                                                        otherNumbers.charAt(1),
                                                        otherNumbers.charAt(2),
                                                        otherNumbers.charAt(3),
                                                        otherNumbers.charAt(4),
                                                        otherNumbers.charAt(5),
                                                        otherNumbers.charAt(6),
                                                        otherNumbers.charAt(7),
                                                        otherNumbers.charAt(8),
                                                        otherNumbers.charAt(9));



        String inputVar = ((CompoundVariable) values[2]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
        return  result;
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters){
        values = parameters.toArray();
    }
}
