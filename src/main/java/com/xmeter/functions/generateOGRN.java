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


public class generateOGRN extends AbstractFunction{
    private static final List<String> desc = new LinkedList<String>();
    private Object[] values; // The value of the passed parameter
    Random rd = new Random();
    private static final String MyFunctionName = "__generateOGRN"; //function name

    public int randomFunc(int min, int max)
    {
        return rd.nextInt((max-min) + 1) + min;
    }


    static {
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    @Override
    public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

        int[] priznak = {0};
        int[] godreg = {0,0};
        int[] region = {0,0};
        int[] num = {0,0,0,0,0,0,0};
        int kontr;
        int i=0;
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
        //if(kontr == 10) kontr=0;

        result = String.format("%d%d%d%d%d%d%d%d%d%d%d%d%d", priznak[0],godreg[0],godreg[1],region[0], region[1],num[0],num[1],num[2],num[3],num[4],num[5],num[6],kontr);


        if (values.length > 0)
        {
            JMeterVariables vars = getVariables();
            String userVariable = ((CompoundVariable) values[0]).execute().trim();
            vars.put(userVariable, result);
        }
        return result;
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