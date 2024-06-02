package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import static com.xmeter.utils.customFunctionUtils.randomFunc;


public class generateInnNatural extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_generateInnNatural"; //function name
    static {
        desc.add("Format string for INN (use 'x' for numbers) (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    @Override
    public String execute(SampleResult arg0, Sampler arg1) {

        String resultFormatString = ((CompoundVariable) values[0]).execute().trim();

        if (resultFormatString.isEmpty())
            resultFormatString = "%d%d%d%d%d%d%d%d%d%d%d%d";
        else
        {
            resultFormatString = resultFormatString.replace("%", "%%");
            for (int i = 0; i < 12; i++)
                resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%d");
            resultFormatString = resultFormatString.replace("\\x", "x");
        }

        int[] region = {0,0};
        int[] inspection = {0, 0};
        int[] num = {0,0,0,0,0,0};
        int[] kontr = {0,0};
        int i;

        while (region[0] == 0 && region[1] == 0)
        {
            region[0] = randomFunc(0, 9);
            region[1] = randomFunc(0, 9);
        }

        while(inspection[0] == 0 &&  inspection[1] == 0)
        {
            inspection[0] = randomFunc(0, 9);
            inspection[1] = randomFunc(0, 9);
        }

        for(i=0;i<6;i++) num[i] = randomFunc(0, 9);

        kontr[0] = ((7*region[0]+
                2*region[1]+
                4*inspection[0]+
                10*inspection[1]+
                3*num[0]+
                5*num[1]+
                9*num[2]+
                4*num[3]+
                6*num[4]+
                8*num[5])%11)%10;

        kontr[1] = ((3*region[0]+
                7*region[1]+
                2*inspection[0]+
                4*inspection[1]+
                10*num[0]+
                3*num[1]+
                5*num[2]+
                9*num[3]+
                4*num[4]+
                6*num[5]+
                8*kontr[0])%11)%10;



        String result = String.format(resultFormatString, region[0], region[1],inspection[0],inspection[1],num[0],num[1],num[2],num[3],num[4],num[5],kontr[0],kontr[1]);


        String inputVar = ((CompoundVariable) values[1]).execute().trim();
        if (!inputVar.isEmpty())
        {
            JMeterVariables vars = getVariables();
            vars.put(inputVar, result);
        }

        return  result;
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