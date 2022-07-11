package stem.drsan.mamritat.mushikha.mrityur.matrixanddeterminantn;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by D on 3/3/2016.
 */
public class Utilities {
    private int iDF = 0;
    private int n = 4;
    public String getSdcard() {
        try {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return null;
            }
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } catch (Exception ignored) {
            return null;
        }
        //	ExternalStorageDirectory;
    }

    public String paste_me(Activity activity) {
        String mumu = null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboarda = (android.text.ClipboardManager) activity.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                mumu = clipboarda.getText().toString();
            } else {
                ClipboardManager clipboardb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // Gets a content resolver instance
                // ContentResolver cr = getContentResolver();

                // Gets the clipboard data from the clipboard
                // ClipData clip = clipboardb.getPrimaryClip();
                // if (clip != null) {

                if (clipboardb.hasPrimaryClip()) {
                    ClipDescription description = clipboardb.getPrimaryClipDescription();
                    ClipData data = clipboardb.getPrimaryClip();
                    if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                        mumu = String.valueOf(data.getItemAt(0).getText());
                }
            }
        } catch (Exception ignored) {
        }

        return mumu;
    }

    public boolean Copy_Clip(String string, Activity activity) {
        try {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) activity.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(string);
            } else {
                ClipboardManager clipboard = (ClipboardManager) activity.getApplicationContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData
                        .newPlainText(
                                activity.getApplicationContext().getResources().getString(
                                        R.string.message), string);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Store a string to the file
     *
     * @param s string
     * @param f file
     * @throws IOException // * @throws //ComicSDCardFull
     */
    public boolean storeStringinFile(String s, File f) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            bw.write(s);
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public float[][] AddMatrix(float[][] a, float[][] b) throws Exception {
        int tms = a.length;
        int tmsB = b.length;
        if (tms != tmsB) {
            throw new Exception("Matrix Size Mismatch");
        }

        float matrix[][] = new float[tms][tms];

        for (int i = 0; i < tms; i++)
            for (int j = 0; j < tms; j++) {
                matrix[i][j] = a[i][j] + b[i][j];
            }

        return matrix;
    }

    // --------------------------------------------------------------
    public float[][] MultiplyMatrix(float[][] a, float[][] b) throws Exception {
        //For Matrix A and B A[mXn] ~ B[nXi]
        //A Row X Column == a.[0]length and a.length assuming all rows equal
        //B Row X Column == b[0].length and b.length assuming all rows equal
        //TODO Check if all Column are equal before computation
        int Sum_row=a[0].length;
        int Sum_col=b.length;
        int A_Column=a.length;//==b.length;
        if(a.length != b[0].length)
            throw new Exception("Matrices incompatible for multiplication");
        if(a[0].length!=b.length)
            throw new Exception(":-(( Please make matrix into square by padding with zeros.:-(((");

        float matrix[][] = new float[Sum_row][Sum_col];
                //a.length][b[0].length];

        for (int i = 0; i < Sum_row; i++)
        {for (int j = 0; j < Sum_col; j++)
            {matrix[i][j] = 0;}}

        //cycle through answer matrix
        for(int i = 0; i < Sum_row; i++){
            for(int j = 0; j < Sum_col; j++){
                for(int k=0;k<A_Column;k++){
                    matrix[i][j]+=a[i][k]*b[k][j];
                }
               // matrix[i][j] = calculateRowColumnProduct(a,i,b,j);
            }
        }
        return matrix;
    }

    public float calculateRowColumnProduct(float[][] A, int row, float[][] B, int col){
        float product = 0;
        for(int i = 0; i < A[row].length; i++)
            product +=A[row][i]*B[i][col];
        return product;
    }
    // --------------------------------------------------------------

    public float[][] Transpose(float[][] a) {

        float m[][] = new float[a[0].length][a.length];

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                m[j][i] = a[i][j];
        return m;
    }

    // --------------------------------------------------------------

    public float[][] Inverse(float[][] a) throws Exception {
        // Formula used to Calculate Inverse:
        // inv(A) = 1/det(A) * adj(A)

        int tms = a.length;

        float m[][] = new float[tms][tms];
        float mm[][] = Adjoint(a);

        float det = Determinant(a);
        float dd = 0;

        if (det == 0) {
            throw  new Exception("Determinant Equals 0, Not Invertible.");

        } else {
            dd = 1 / det;
        }

        for (int i = 0; i < tms; i++)
            for (int j = 0; j < tms; j++) {
                m[i][j] = dd * mm[i][j];
            }

        return m;
    }

    // --------------------------------------------------------------

    public float[][] Adjoint(float[][] a) throws Exception {

        int tms = a.length;

        float m[][] = new float[tms][tms];

        int ii, jj, ia, ja;
        float det;

        for (int i = 0; i < tms; i++)
            for (int j = 0; j < tms; j++) {
                ia = ja = 0;

                float ap[][] = new float[tms - 1][tms - 1];

                for (ii = 0; ii < tms; ii++) {
                    for (jj = 0; jj < tms; jj++) {

                        if ((ii != i) && (jj != j)) {
                            ap[ia][ja] = a[ii][jj];
                            ja++;
                        }

                    }
                    if ((ii != i) && (jj != j)) {
                        ia++;
                    }
                    ja = 0;
                }

                det = Determinant(ap);
                m[i][j] = (float) Math.pow(-1, i + j) * det;
            }

        m = Transpose(m);

        return m;
    }

    // --------------------------------------------------------------




    public float[][] UpperTriangle(float[][] m) {


        float f1 = 0;
        float temp = 0;
        int tms = m.length; // get This Matrix Size (could be smaller than
        // global)
        int v = 1;

        iDF = 1;

        for (int col = 0; col < tms - 1; col++) {
            for (int row = col + 1; row < tms; row++) {
                v = 1;

                outahere: while (m[col][col] == 0) // check if 0 in diagonal
                { // if so switch until not
                    if (col + v >= tms) // check if switched all rows
                    {
                        iDF = 0;
                        break outahere;
                    } else {
                        for (int c = 0; c < tms; c++) {
                            temp = m[col][c];
                            m[col][c] = m[col + v][c]; // switch rows
                            m[col + v][c] = temp;
                        }
                        v++; // count row switchs
                        iDF = iDF * -1; // each switch changes determinant
                        // factor
                    }
                }

                if (m[col][col] != 0) {


                    try {
                        f1 = (-1) * m[row][col] / m[col][col];
                        for (int i = col; i < tms; i++) {
                            m[row][i] = f1 * m[col][i] + m[row][i];
                        }
                    } catch (Exception e) {
                        return null;
                    }

                }

            }
        }

        return m;
    }

    public float Determinant(float[][] matrix) {
        /*if (INFO) {
            System.out.println("Getting Determinant...");
        }*/

            int tms = matrix.length;

            float det = 1;

            matrix = UpperTriangle(matrix);

            for (int i = 0; i < tms; i++) {
                det = det * matrix[i][i];
            } // multiply down diagonal

            det = det * iDF; // adjust w/ determinant factor


            return det;

    }

   public String DisplayMatrix(float[][] matrix) {

//A Row X Column == a.length and a.length[0] assuming all rows equal
        //if (DEBUG) {
        //System.out.println("Displaying Matrix");
        //}

    String rstr = "";
    String dv = "";
    int MCol=matrix.length;
    //Last Row as Column count?
    int MRow=matrix[MCol-1].length;

    for (int i = 0; i <MCol ; i++) {
        //All Ccolumns have equal no of  items yes matrix[0].length=matrix[1].length=... matrix[Mrow].length
        for (int j = 0; j < MRow; j++) {

             dv = String.format("%.2f", matrix[i][j]);
            //nf.format(matrix[i][j]);
            rstr = rstr.concat(dv + "  ");
        }

        rstr = rstr.concat("\n");
    }
    return rstr;
    //output.setText(rstr);

    }

    public float[][] ReadInMatrix(String rawtext) throws Exception {



            if (rawtext.length() <= 0)
                return null;

		/* == Parse Text Area == */
            //String rawtext = ta.getText();
            String val = "";
            int i = 0;
            int j = 0;
            int[] rsize = new int[Constants.max];


            StringTokenizer ts = new StringTokenizer(rawtext, "\n");
            while (ts.hasMoreTokens()) {
                if (ts.toString().length() >= 3) {
                    StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
                    while (ts2.hasMoreTokens()) {

                        ts2.nextToken();
                        j++;
                    }
                    rsize[i] = j;
                    i++;
                    j = 0;
                }
            }


            for (int c = 0; c < i; c++) {

                if (rsize[c] != i) {
                    //status.setText("Invalid Matrix Entered. Size Mismatch.");

                    throw new Exception("Invalid Matrix Entered. Size Mismatch.");

                }
            }
		/* == set matrix size == */
             n = i;
            //List<List<Float>> matrics;
            //matrics=List<List<Float>>();
            float matrix[][] = new float[n][n];
            i = j = 0;
            val = "";

		/* == Do the actual parsing of the text now == */
            StringTokenizer st = new StringTokenizer(rawtext, "\n");
            while (st.hasMoreTokens()) {
                StringTokenizer st2 = new StringTokenizer(st.nextToken());
                while (st2.hasMoreTokens()) {
                    val = st2.nextToken();
                    try {

                        matrix[i][j] = Float.valueOf(val);
                    } catch (Exception exception) {
                        throw new Exception("Invalid Number Format");
                        //return null;
                    }
                    j++;
                }
                i++;
                j = 0;
            }


            return matrix;

    }


    public float[][] ReadInMatrix_mod(String rawtext) throws Exception {



            if (rawtext.length() <= 0)
                return null;

		/* == Parse Text Area == */
            //String rawtext = ta.getText();
            String val = "";
            int i = 0;
            int j = 0;
            int m=0;
            int[] rsize = new int[Constants.max];

            for (int c = 0; c < i; c++) {

                if (rsize[c] != i) {
                    //status.setText("Invalid Matrix Entered. Size Mismatch.");

                    throw new Exception("Invalid Matrix Entered. Size Mismatch.");

                }
            }
		/* == set matrix size == */
            n = i;

            StringTokenizer ts = new StringTokenizer(rawtext, "\n");
            while (ts.hasMoreTokens()) {
                if (ts.toString().length() >= 3) {
                    StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
                    while (ts2.hasMoreTokens()) {

                        ts2.nextToken();
                        j++;
                    }
                    rsize[i] = j;
                    i++;
                    j = 0;
                    m++;
                }
            }



		/* == set matrix size == */
            n = i;
    //Toast.makeText(ge)
            float matrix[][] = new float[n][m];
            i = j = 0;
            val = "";

		/* == Do the actual parsing of the text now == */
            StringTokenizer st = new StringTokenizer(rawtext, "\n");
            while (st.hasMoreTokens()) {
                StringTokenizer st2 = new StringTokenizer(st.nextToken());
                while (st2.hasMoreTokens()) {
                    val = st2.nextToken();
                    try {
                        matrix[i][j] = Float.valueOf(val);
                    } catch (Exception exception) {
                        // status.setText("Invalid Number Format");
                        return null;
                    }
                    j++;
                }
                i++;
                j = 0;
            }


            return matrix;

    }
   /* public String DisplayMatrix(float[][] matrix) {
        try {
            String item = "", line = "";
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    item = String.format("%1$.2f", matrix[i][j]);
                    //nf.format(matrix[i][j]);
                    line = line.concat(item + "  ");
                }

                line = line.concat("\n");
            }
            //line.concat("|");
            return line;
        } catch (Exception e) {
            return null;
        }
    }*/
    /*public float[][] Pad_matrix_to_square(float[][] matrix){
        if(get_Column_count(matrix)!=get_Row_count(matrix))
        {
            float Return_matrix[][] = new float[matrix[0].length][matrix[0].length];
            for (int i = 0; i < matrix[0].length; i++)
            {for (int j = 0; j < matrix[0].length; j++)
            {Return_matrix[i][j] = 0;

            }}



                String rstr = "";
                String dv = "";
                int MCol=matrix.length;
                int MRow=matrix[0].length;
                //Last Row as Column count?
                //int MRow=matrix[MCol-1].length;
                //float Return_matrix[][] = new float[MCol][MCol];
                for (int i = 0; i <MCol ; i++) {
                    //All Ccolumns have equal no of  items yes matrix[0].length=matrix[1].length=... matrix[Mrow].length
                    for (int j = 0; j < MRow; j++) {


                            Return_matrix[i][j]=matrix[i][j];
                            //if(matrix[i][j])
                        //dv = String.format("%.2f", matrix[i][j]);
                        //nf.format(matrix[i][j]);
                        //rstr = rstr.concat(dv + "  ");
                    }

                    //rstr = rstr.concat("\n");
                }
                return Return_matrix;
                //output.setText(rstr);
            }
         return matrix;

    }*/
    public int get_Column_count(float[][] matrix) {
        if (matrix.length>0)
            //Assumes all Columns are equal 1 2 3 4
        //                                  4 5 6 7  Horizontal Cloumn=4 Vertical Row 2 [RC]
            return matrix[0].length;
        else
            return 0;
    }

    public int get_Row_count(float[][] matrix) {
        if (matrix.length>0)
        return matrix.length;
        else
            return 0;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////--TODO------------------------------------------------------------////////////////////////////////
    public float[][] LoadArrayinto2DArray(float table[][],float Some_values[],int row,int Column){
        try{
            int Mcol=table.length;
            int MRow=table[Mcol-1].length;
            if(Some_values.length!=MRow)
                throw new Exception("Please ensure both matrix have same number of rows!");

            for (int i = 0; i < Mcol; i++){
                for (int j = 0; j < MRow; j++)
                {
                    if((j==row)&&(i==Column))
                    {
                        table[i][j]=Some_values[i];
                    }
                }}


            return table;
        } catch (Exception e) {

            return null;
        }
    }
////////////////////////////////////////////////////////////////////////
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

    public float[][] Stringto2DArray(String input) {
        //int i=input.replaceAll("\\n","").length()+1;
        try {
            input=input.trim();
            //matrix_A=null;
            Pattern p=Pattern.compile("\\r?\\n");

            String lines[] =p.split(input);// input.split(p);
            int count_lines = lines.length;
            String line[];
            int j= lines[lines.length-1].split("\\s+").length;

            //Toast.makeText(getApplicationContext(),Integer.toString(count_lines)+"======"+Integer.toString(j),Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),Integer.toString(count_lines),Toast.LENGTH_LONG).show();
            float matrix_A[][] = new float[count_lines][j];

            //A.setLines(count_lines);
            j=0;
            for (int i = 0; i < count_lines; i++) {
                line = lines[i].split("\\s+");
                for (j = 0; j < line.length; j++)
                {   matrix_A[i][j] = Float.parseFloat(line[j]);}
                //Toast.makeText(getApplicationContext(),line[j],Toast.LENGTH_LONG).show();}

                //}
                //Toast.makeText(getApplicationContext(),Integer.toString(line.length),Toast.LENGTH_LONG).show();

            }

            return matrix_A;
        } catch (Exception e) {
            return null;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public String Show2DArray(double[][] matrixA) {
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
            result += String.format("%.2f", b)+"  ";
        result += "\n";
    }


    return result;
}
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
    public String Show1DArray_MC_InColumn(double matrixA[]) {
        if((matrixA.length<=0))
            return "";
        String result = "";

        for(double a:matrixA)
            result+= result += String.format("%.2f",a)+"\n";

        return result;
    }
    public String Show1DArray_MC_InColumn_EGV(double matrixA[]) {
        if((matrixA.length<=0))
            return "";
        String result = "";

        for(double a:matrixA)
            result+= result += String.format("(%.2f-L)",a)+" ";

        return result;
    }
}