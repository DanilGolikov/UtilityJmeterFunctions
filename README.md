# Custom_jmeter_functions

Custom functions for jmeter

Ready-made functions:
- `generateEmail` - a function that generates a email
    - parametrs:
        - **List domain(s) (use | as separator)**: a list of domains that will be randomly inserted at the end of the email
        - **Use chars in name email (Optional)**: list of characters to be used in generating the email name
        - **Minimum length of the email name (min 1) (Optional)**
        - **Maximum length of the email name (max 64) (Optional)**

    ![](./README_GIFs/generateEmail.gif)
--- 
 - `generateInnLegal` - a function that generates a valid INN for a legal entity

    ![](./README_GIFs/generateInnLegal.gif)
 ---
 - `generateInnNatural` - a function that generates a valid INN for a natural entity

    ![](./README_GIFs/generateInnNatural.gif)
 ---
 - `generateOgrn` - a function that generates a valid OGRN

   ![](./README_GIFs/generateOgrn.gif)
 ---
- `generatePhoneNumber` - a function that generates a phone number
    - parameters:
        - **Country code(s) (use | as separator)**

  ![](./README_GIFs/generatePhoneNumber.gif)
---
- `generatePinCode` - a function that generates a PIN_CODE
    - parameters:
        - **PIN-code length**: specifies the length of the PIN code. If the length of the generated number   
          is less than the entered one, then the number will align to the entered length
        - **The minimum value allowed for a range of values (optional)**
        - **The maximum value allowed for a range of values (optional)**

  ![](./README_GIFs/generatePinCode.gif)
---
 - `generateSnils` - a function that generates a valid SNILS

   ![](./README_GIFs/generateSnils.gif)
---
 - `jsonPathFromVar` - allows extracting values from a variable that stores JSON using JsonPath
   - parameters:
     - **Target variable** - a variable to which JsonPath will be applied
     - **JsonPath expression** - JsonPath expression ._.

       ![](./README_GIFs/jsonPathFromVar.gif)
---
 - `randomStringLiteral`:
   - parameters:
     - **List string literals (use | as separator)**
     - **Use a separator (Optional)**

   ![](./README_GIFs/randomStringLiteral.gif)
---
 - `samplerComment` - a function that returns a comment of the element in which it is called. Like samplerName, only samplerComment

   ![](./README_GIFs/samplerComment.gif)
---
 - `timeRandom` - generates a random date within the specified range
   - parameters:
     - **Start time** - start date of the range
     - **End time** - end date of the range

     can be relative, for example: now; now-5m; now-10d, now+1y.
     - **Symbols**
       - **s** - seconds
       - **m** - minutes
       - **h** - hours
       - **d** - days
       - **w** - weeks
       - **M** - month
       - **y** - years
     - **Time format (default timestamp)** - the format in which the random date will be returned

   ![](./README_GIFs/timeRandom_relative.gif)
   ![](./README_GIFs/timeRandom_absolute.gif)
---
Version Jmeter: 5.4.3   
The Custom_functions-x.x.x.jar should be put in %jmeter%\lib\ext


