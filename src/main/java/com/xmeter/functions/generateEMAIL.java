package com.xmeter.functions;

import java.util.*;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;



public class generateEMAIL extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__generateEMAIL"; //function name

    Random rd = new Random();
    public int randomFunc(int min, int max)
    {
        return rd.nextInt((max-min) + 1) + min;
    }


    static {

        desc.add("List domain(s) (use | as separator)");
        desc.add("Use chars in name email (Optional)");
        desc.add("Minimum length of the email name (min 1) (Optional)");
        desc.add("Maximum length of the email name (max 64) (Optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    public char generateChar(String possibleChars, boolean notDotChar)
    {
        if (notDotChar)
        {
            char symbol = possibleChars.charAt(randomFunc(0, possibleChars.length()-1));
            while (symbol == '.')
                symbol = possibleChars.charAt(randomFunc(0, possibleChars.length()-1));
            return symbol;

        }
        return possibleChars.charAt(randomFunc(0, possibleChars.length()-1));
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String[] domainEmail_input = ((CompoundVariable) values[0]).execute().trim().split("\\|");
        if (Objects.equals(domainEmail_input[0], ""))
            throw new InputMismatchException("Impossible to generate email without a domain");
        String domainEmail = domainEmail_input[randomFunc(0, domainEmail_input.length-1)];

        StringBuilder nameEmail = new StringBuilder();


        String nameEMAILsymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!#$%&'*+-/=?^_`{|}~.";

        String useNameEmailChars_input = ((CompoundVariable) values[1]).execute().trim();

        if (!useNameEmailChars_input.equals("")) {

            int troubleChars = 0;
            for (char symbol : useNameEmailChars_input.toCharArray())
            {
                if (!(nameEMAILsymbols.contains(Character.toString(symbol))))
                    throw new InputMismatchException("Failed to generate with such a symbol:" + symbol +
                            "Symbols that can be used: \"a-zA-Z0-9!#$%&'*+-/=?^_`{|}~.\")");

                if (symbol == '.')
                    troubleChars++;
            }

            if (troubleChars == useNameEmailChars_input.length())
                throw new InputMismatchException("Failed to generate an email name with this character set");

            nameEMAILsymbols = useNameEmailChars_input;
        }



        int lenNameEmailMinimum = 1;
        int lenNameEmailMaximum = 64;

        String lenNameEmail_input = ((CompoundVariable) values[2]).execute().trim();
        if (!lenNameEmail_input.equals(""))
        {
            lenNameEmailMinimum = Integer.parseInt(lenNameEmail_input);
            if (lenNameEmailMinimum < 1 || lenNameEmailMinimum > 64)
                throw new InputMismatchException("The length of the email name was entered incorrectly (possible length from 1 to 64)");
        }

        lenNameEmail_input = ((CompoundVariable) values[3]).execute().trim();
        if (!lenNameEmail_input.equals(""))
        {
            lenNameEmailMaximum = Integer.parseInt(lenNameEmail_input);
            if (lenNameEmailMaximum < 1 || lenNameEmailMaximum > 64)
                throw new InputMismatchException("The length of the email name was entered incorrectly (possible length from 1 to 64)");
        }
        int lenNameEmail = randomFunc(lenNameEmailMinimum, lenNameEmailMaximum);

        boolean lastDot = false;
        nameEmail.append(generateChar(nameEMAILsymbols, true));
        while (nameEmail.length() < lenNameEmail-1)
        {
            char symbol = generateChar(nameEMAILsymbols, lastDot);
            lastDot = symbol == '.';
            nameEmail.append(symbol);
        }
        if (nameEmail.length() < lenNameEmail)
            nameEmail.append(generateChar(nameEMAILsymbols, true));


        String result = nameEmail + "@" + domainEmail;

        String inputVar = ((CompoundVariable) values[4]).execute().trim();
        if (!inputVar.equals(""))
        {
            JMeterVariables vars = getVariables();
            vars.put(inputVar, result);
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