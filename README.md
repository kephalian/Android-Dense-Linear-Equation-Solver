# Android-Dense-Linear-Equation-Solver
Solve dense linear equations of any SIZE (N) on Android cell phones using an implemention of Apache.Math.Common on Android Java. 

You can study the difficult workings of this program or you can get busy and download the attached APK file (Trust me !) and start solving linear equations.
The the heart of the program is this code
```
public void Compute_solutions_MathCommons(){
        try{
        //SAFE TRY
        double[][] Matrix;
             double[] vectorB;
            String tmp1 = "";
            String tmp;
```
### Variables initialized, these Predefined functions which are used to convert String to a Vector and a Determinant MATRIX, depicted next
```

            
            
            // Converts to  a Vector
            vectorB=utilities.Stringto1DArray_MC(B.getText().toString());
            
            //Converts to Matrix
            Matrix=utilities.Stringto2DArray_MC(A.getText().toString());

```
### Determinant has to be square
```

            // Determinant has to be square
            if(Matrix[0].length!=Matrix.length)
                throw new Exception("Matrix is not Square");
                
            // Determinant must not be NULL
            
```
### Determinant must not be NULL
```         

            if((Matrix==null)||(vectorB==null))
            throw new Exception("Matrix is null");

//// MAGIC OF JAVA COMMON MATH LIBARARY ON AN ANDROID PHONE!!
```
### MAGIC!
### MAGIC OF JAVA COMMON MATH LIBARARY ON AN ANDROID PHONE!!
```
 
            RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
            DecompositionSolver solver=new LUDecomposition(matrix).getSolver();
            
// SQUARE MATRIX DETERMINANT
            Double solution=new LUDecomposition(matrix).getDeterminant();
            
  // LOADING INTO STRING
  // CHANGE PRECISION SOS %.2f
            tmp=String.format("Determinant |A|=%.2f\n", solution);
            
 /// SOLUTION OF LINEAR EQUATION EXISTS
            if(solution!=0) {
                RealVector constants = new ArrayRealVector(vectorB, false);
                RealVector solution2 = solver.solve(constants);


                for (int i = 0; i < solution2.getDimension(); i++)
                    tmp1 += String.format("\nX%d = %.2f", i, solution2.getEntry(i));
               // double xn=solution/values;
               // tmp=String.format(getResources().getString(R.string.Cr_result),counter,solution,values,xn);
                tmp+=tmp1;
            }
            else
         
            tmp="|A| Determinant is Zero.No solution exists.\n";

            //Toast.makeText()

          /*  if(counter==0)
            {
                values=Float.parseFloat(String.format("%.2f",solution));
                counter++;}
            else
            {*/




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
