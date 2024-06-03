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


public class generateOgrn extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_generateOgrn"; //function name
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
            resultFormatString = "%d%d%d%d%d%d%d%d%d%d%d%d%d";
        else
        {
            resultFormatString = resultFormatString.replace("%", "%%");
            for (int i = 0; i < 13; i++)
                resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%d");
            resultFormatString = resultFormatString.replace("\\x", "x");
        }

        int[] priznak = {0};
        int[] godreg = {0,0};
        int[] region = {0,0};
        int[] num = {0,0,0,0,0,0,0};
        int kontr;
        int i;
        int count;
        String part;
        String result;


        priznak[0] = randomFunc(1,2);
        if(priznak[0] == 2) priznak[0] = 5;

        godreg[0] = randomFunc(0,2);
        godreg[1] = randomFunc(0,9);

        while (region[0] == 0 &&  region[1] == 0)
        {
            region[0] = randomFunc(0,9);
            region[1] = randomFunc(0,9);
        }

        for(i=0;i<7;i++)
        {
            num[i] = randomFunc(0,9);
        }

        part = String.format("%d%d%d%d%d", priznak[0],godreg[0],godreg[1],region[0],region[1]);
        count= Integer.parseInt(part);
        kontr = count%11;
        part = String.format("%d%d%d%d%d%d%d%d", kontr,num[0],num[1],num[2],num[3],num[4],num[5],num[6]);
        count = Integer.parseInt(part);
        kontr = (count%11)%10;

        result = String.format(resultFormatString, priznak[0],godreg[0],godreg[1],region[0], region[1],num[0],num[1],num[2],num[3],num[4],num[5],num[6],kontr);


        String inputVar = ((CompoundVariable) values[1]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
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