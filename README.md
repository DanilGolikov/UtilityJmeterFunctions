# Custom_jmeter_functions

Custom functions for jmeter

Ready-made functions:
 - `generateINN_legal` - a function that generates a valid INN for a legal entity
 
 - `generateINN_natural` - a function that generates a valid INN for a natural entity
 
 - `generateOGRN` - a function that generates a valid OGRN
 
 - `generateSNILS` - a function that generates a valid SNILS
 
 - `generatePIN_CODE` - a function that generates a PIN_CODE   
   - parameters:
     - **PIN-code length**: specifies the length of the PIN code. If the length of the generated number   
           is less than the entered one, then the number will align to the entered length 
     - **The minimum value allowed for a range of values (optional)**
     - **The maximum value allowed for a range of values (optional)**
     
 - `generateEMAIL` - a function that generates a email   
   - parametrs:
     - **List domain(s) (use | as separator)**: a list of domains that will be randomly inserted at the end of the email
     - **Use chars in name email (Optional)**: list of characters to be used in generating the email name
     - **Minimum length of the email name (min 1) (Optional)**
     - **Maximum length of the email name (max 64) (Optional)**
     
 - `randomStringLiteral`:
   - parameters:
     - **List string literals (use | as separator)**
     - **Use a separator (Optional)**
     

Version Jmeter: 5.4.3   
The target\Custom_functions-1.0.0.jar should be put in %jmeter%\lib\ext

![image](https://user-images.githubusercontent.com/80261859/178257439-a4f97257-3f1d-4485-837e-b1f409a9be72.png)

