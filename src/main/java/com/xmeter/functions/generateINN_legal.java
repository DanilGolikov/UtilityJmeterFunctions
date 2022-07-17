package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;


public class generateINN_legal extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter
    Random rd = new Random();
    private static final String MyFunctionName = "__generateINN_legal"; //function name

    public int randomFunc(int min, int max)
    {
        return rd.nextInt((max-min) + 1) + min;
    }

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

        if (resultFormatString.equals(""))
            resultFormatString = "%d%d%d%d%d%d%d%d%d%d";
        else
        {
            resultFormatString = resultFormatString.replace("%", "%%");
            for (int i = 0; i < 10; i++)
                resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%d");
            resultFormatString = resultFormatString.replace("\\x", "x");
        }




        int[] region = {0,0};
        int[] inspection = {0, 0};
        int[] num = {0,0,0,0,0};
        int[] kontr = {0};

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

        for(int i=0;i<5;i++) num[i] = randomFunc(0, 9);

        kontr[0] = ((2*region[0] +
                4*region[1]+
                10*inspection[0]+
                3*inspection[1]+
                5*num[0] +
                9*num[1]+
                4*num[2]+
                6*num[3]+
                8*num[4])%11)%10;


        String result = String.format(resultFormatString, region[0], region[1],inspection[0],inspection[1],num[0],num[1],num[2],num[3],num[4],kontr[0]);



        String inputVar = ((CompoundVariable) values[1]).execute().trim();
        if (!inputVar.equals(""))
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
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        values = parameters.toArray();
    }
}