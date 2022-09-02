# Android-Dense-Linear-Equation-Solver
Solve dense linear equations of any SIZE (N) on Android cell phones using an implemention of Apache.Math.Common on Android Java. 

## Download the compiled APK (works on any current Android phone), copy APK on Sdcard and Install via File manager or ABD 
[Download APK File](https://github.com/kephalian/Android-Dense-Linear-Equation-Solver/blob/main/20220818055414pmNQ44521.pdf) 

## Read the long paper about how this was accomplished and tested for errors NeuroQuantology in July2022 Volume20 Issue8 Page4959-4973 



[Download the Research paper publised in the Neuroquantology journal](https://github.com/kephalian/Android-Dense-Linear-Equation-Solver/blob/main/20220818055414pmNQ44521.pdf)

[From Journal Neuroquantology Site Volume 20, No 8 (2022) Article](https://www.neuroquantology.com/article.php?id=5523)

You can study the difficult workings of this program or you can get busy and download the attached APK file (Trust me !) and start solving linear equations.
## This program also has many other features like computations of Eigenvalues and Eigenvector of Matrices, Determinant, Inverse of a Matrix.


Before you start criticizing me like, the one chinese scholars who called my work trivial,uninteresting and non-academic one. This was implemented in 2016 long before Pydroid, Numpy and SymPy but not published (obviously). This is Standalone Android GUI Program implemented in JAVA and not in Numpy/Scipy/ Sympy CPython running off PyDroid emulator for Android (which can do this more efficiently). 

This App was not released on PlayStore as I could not get Android Studio to install (duh!) and compile Hello world!, consequently could not fullfill their minimum target API,  then they shifted to Kotlin and now to Dart/ Flutter (_both of which are beyond me, the infinite brackets {} paradigm of Flutter is hell, **I don't know who LOVES infinite {{{{}}}}}}}} nested brackets of Flutter**_).





The the heart of the program is this code

```java
public void Compute_solutions_MathCommons(){
        try{
        //SAFE TRY
        double[][] Matrix;
             double[] vectorB;
            String tmp1 = "";
            String tmp;
```
### Variables initialized, these Predefined functions which are used to convert String to a Vector and a Determinant MATRIX, depicted next
```java

            
            
            // Converts to  a Vector
            vectorB=utilities.Stringto1DArray_MC(B.getText().toString());
            
            //Converts to Matrix
            Matrix=utilities.Stringto2DArray_MC(A.getText().toString());

```
### Determinant has to be square
```java

            // Determinant has to be square
            if(Matrix[0].length!=Matrix.length)
                throw new Exception("Matrix is not Square");
                
            // Determinant must not be NULL
            
```
### Determinant must not be NULL
```java         

            if((Matrix==null)||(vectorB==null))
            throw new Exception("Matrix is null");

//// MAGIC OF JAVA COMMON MATH LIBARARY ON AN ANDROID PHONE!!
```
### MAGIC!
### MAGIC OF JAVA COMMON MATH LIBARARY ON AN ANDROID PHONE!!
```java
 
            RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
            DecompositionSolver solver=new LUDecomposition(matrix).getSolver();
```            
### SQUARE MATRIX DETERMINANT
```java
            Double solution=new LUDecomposition(matrix).getDeterminant();
 ```     
 ### LOADING INTO STRING
 ### CHANGE PRECISION SOS %.2f
 ```java
            tmp=String.format("Determinant |A|=%.2f\n", solution);
```        
### SOLUTION OF LINEAR EQUATION EXISTS
```java
            if(solution!=0) {
                RealVector constants = new ArrayRealVector(vectorB, false);
                RealVector solution2 = solver.solve(constants);


                for (int i = 0; i < solution2.getDimension(); i++)
                    tmp1 += String.format("\nX%d = %.2f", i, solution2.getEntry(i));
                    tmp+=tmp1;}
```        
        
## Make the case for NO SOLUTION
```java  
   else
         
            tmp="|A| Determinant is Zero.No solution exists.\n";

            //Toast.makeText()
       

```        
### CONGRATULATIONS YOU JUST SOLVED THE EQUATION

### Note since the number of variables solvable is unlimited x is denoted by xn, y as x2, z as x3, x6 so on till infinity 
### Notation of list variables (a, b,c,d,e so on till inifnity) is Xn or Xinfinity
### Update the GUI LISTVIEW WITH THE SOLUTIONS
```java

            listView2.startAnimation(drop);
            Snackbar.make(findViewById(android.R.id.content), tmp, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            //ArrayAdapter<String> madapt;
            // madapt = new ArrayAdapter<>(this, R.layout.mylayout, R.id.label);
            madapt.add(tmp);
            madapt.notifyDataSetChanged();
            //listView.set
            listView2.setAdapter(madapt);

        //RealMatrix pInverse=new LUDecomposition(matrix).getSolver().getInverse();
        // pInverse.getData();
        //A.setText(Show2DArray_MathCommons(pInverse.getData()));
            Save_state();
        } catch (Exception e){
            Snackbar.make(findViewById(android.R.id.content), e.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
```
### Loading and converting STRING TO/FROM MATRIX/VECTOR variables into Matrix and Vector- UTILITIES
```java
public String Show1DArray_MC(double matrixA[]) {
    if((matrixA.length<=0))
        return "";
    String result = "";

    for(double a:matrixA)
    result+= result += String.format("%.2f",a)+"  ";

    return result;
}
    /////////////////////////////////////////////////////////////////////
    public String Show2DArray_MC(double matrixA[][]) {
        if((matrixA.length<=0)||(matrixA[0].length<=0))
                return "";
        String result = "";
        //       float n[];

        /*for(int i=0;i<matrixA.length;i++)
        {
            for(int j=0;j<matrixA[i].length;j++)
               result+=Float.toString(matrixA[i][j]);

            result+="\n";
        }*/
        for (double a[] : matrixA) {
            for (double b : a)
                result += String.format("%.2f",b)+"  ";
            result += "\n";
        }


        return result;
    }
    //////////////////////////////////////////////////////////////////
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public double[][] Stringto2DArray_MC(String input) {
        //int i=input.replaceAll("\\n","").length()+1;
        try {
            input=input.trim();
            //matrix_A=null;
            Pattern p=Pattern.compile("\\r?\\n");

            String lines[] =p.split(input);// input.split(p);
            int count_lines = lines.length;
            if (count_lines<=0)
            {return null;}
            String line[];
            int jrow= lines[lines.length-1].split("\\s+").length;
            if (jrow<=0)
            {return null;}
            //Toast.makeText(getApplicationContext(),Integer.toString(count_lines)+"======"+Integer.toString(j),Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),Integer.toString(count_lines),Toast.LENGTH_LONG).show();
            double[][] matrix_A = new double[count_lines][jrow];

           // A.setLines(count_lines);
            //j=0;
            for (int i = 0; i < count_lines; i++) {
                line = lines[i].split("\\s+");
                for (int j = 0; j < line.length; j++)
                {   if(line[j].length()>0)
                    matrix_A[i][j] = Float.parseFloat(line[j].trim());

                }
                //Toast.makeText(getApplicationContext(),line[j],Toast.LENGTH_LONG).show();}

                //}
                //Toast.makeText(getApplicationContext(),Integer.toString(line.length),Toast.LENGTH_LONG).show();

            }

            return matrix_A;
        } catch (Exception e) {
            return null;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public double[] Stringto1DArray_MC(String input) {
    //int i=input.replaceAll("\\n","").length()+1;
    try {
        input=input.trim();
        //matrix_A=null;

        Pattern p=Pattern.compile("\\r?\\n");

        String lines[] =p.split(input);// input.split(p);
        int count_lines = lines.length;
        if (count_lines<=0)
        {return null;}
        //int j= lines[lines.length-1].split("\\s+").length;

        //Toast.makeText(getApplicationContext(),Integer.toString(count_lines)+"======"+Integer.toString(j),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),Integer.toString(count_lines),Toast.LENGTH_LONG).show();
        double[] matrix_A = new double[count_lines];
        String item;


        for (int i = 0; i < count_lines; i++) {
            item=lines[i];
            item=item.trim();
            if(item.length()>0)
                matrix_A[i] = Double.parseDouble(lines[i]);

        }
        //Toast.makeText(getApplicationContext(),line[j],Toast.LENGTH_LONG).show();}

        //}
        //Toast.makeText(getApplicationContext(),Integer.toString(line.length),Toast.LENGTH_LONG).show();



        return matrix_A;
    } catch (Exception e) {
        return null;
    }
}
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
```
### PROGUARD WILL CREATE PROBLEMS SO ADD CLASSES TO EXCLUDE
```java
-keep class org.apache.commons.math3.linear.**
-keep interface org.apache.commons.math3.linear.**
-dontwarn org.apache.commons.math3.geometry.**
```
