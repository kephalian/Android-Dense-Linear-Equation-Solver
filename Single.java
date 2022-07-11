package stem.drsan.mamritat.mushikha.mrityur.matrixanddeterminantn;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.File;
import java.util.StringTokenizer;

public class Single extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText input,output;
    TextView status,resulta;
    Utilities utilities;
    double[][] Matrix;
    Animation drop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        input=(EditText)findViewById(R.id.editText);
        //input.setFilters(InputFilter);
        output=(EditText)findViewById(R.id.editText2);
        status=(TextView) findViewById(R.id.Status);
        resulta=(TextView) findViewById(R.id.box_on_top_result);
        utilities=new Utilities();
         drop = AnimationUtils.loadAnimation(this, R.anim.tween);
            input.startAnimation(drop);
            status.startAnimation(drop);
            resulta.startAnimation(drop);
        output.startAnimation(drop);
        // output.setVisibility(View.INVISIBLE);
        //Load previous value into input box
        Load_state();
///Splash Screen
if(Singlu.getValj()==7)
        {
            Singlu.getInstance().setValj(0);
            //Singlu no longer is 7 so this code will run only once!!


    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater Infla;
    Infla = getLayoutInflater();
    builder.setView(Infla.inflate(R.layout.linku, (ViewGroup) findViewById(R.id.linku_top)));
		builder.setMessage(getString(R.string.app_name));
        builder.setCancelable(true);
       builder.setNegativeButton("       OK      ", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
//            Toast.makeText(getApplicationContext(), "Click the module below or\n Choose from Menu, above", Toast.LENGTH_LONG).show();


        }
    });

    AlertDialog alert = builder.create();

    alert.show();}
	   
	   

	   
	   
	   
       // Snackbar.make(findViewById(android.R.id.content), getString(R.string.Opening_status), Snackbar.LENGTH_LONG)
         //       .setAction("Action", null).show();
        /*nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(decimals);*/
        input.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Single.this, v) {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_share:
                                Actions_do(Constants.Share_Matrices);
                                return true;
                            case R.id.nav_save:
                                Actions_do(Constants.Save_Matrices);
                                return true;
                            case R.id.nav_save_as_PDF:
                                Actions_do(Constants.Save_Matrices_As_Pdf);
                                return true;
                            case R.id.nav_copy:
                                Actions_do(Constants.Copy_Matrix);
                                return true;
                            case R.id.nav_paste:
                                Actions_do(Constants.Paste_Matrix);
                                return true;
                            case R.id.nav_clear_all:
                                Actions_do(Constants.Clear_all);
                                return true;
                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu.inflate(R.menu.popup);
                popupMenu.show();

                //PopupMenu popupMenu = new PopupMenu(Single.this, v){


                return false;
            }
        });
    }
    public void Load_state(){
        SharedPreferences settings = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String saved_value = settings.getString("Safe1", "");
        input.setText(saved_value);
        saved_value = settings.getString("Safe2", "");
        output.setText(saved_value);
    }
public void Save_state(){
    SharedPreferences settings = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("Safe1", input.getText().toString());
    editor.putString("Safe2",output.getText().toString());
    //editor.putBoolean("silentMode", mSilentMode);

    // Commit the edits!
    editor.apply();
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void Actions_do(int Action_index){

        switch (Action_index){
            case Constants.Validate_Matrix:

                try {
                    String str = input.getText().toString();
                    if(utilities.Stringto2DArray_MC(str)!=null)
                    {
                        Matrix=utilities.Stringto2DArray_MC(str);
                        //float matrix_A[][];
                        //matrix_A=utilities.ReadInMatrix(str);
                        int col=Matrix.length;
                        int row=Matrix[col-1].length;
                        if(col!=row)
                        {input.setText("");
                            status.setText("Error:Matrix A is Not a Square Matrix Row="+Integer.toString(row)+" Column="+Integer.toString(col));
                            return;
                        }
                        status.setText("Matrix A is Valid. and Row="+Integer.toString(row)+" Column="+Integer.toString(col));
                        Snackbar.make(findViewById(android.R.id.content), "Matrix A Valid", Snackbar.LENGTH_LONG).setAction("Action", null).show();}
                    //DisplayMatrix(utilities.ReadInMatrix(str));
                   /* str=output.getText().toString();

                    if(utilities.ReadInMatrix(str)!=null)
                    {float matrix_A[][];
                        matrix_A=utilities.ReadInMatrix(str);
                        int col=utilities.get_Column_count(matrix_A);
                        int row=utilities.get_Row_count(matrix_A);
                        output.setVisibility(View.VISIBLE);

                        Toast.makeText(getApplicationContext(),"Matrix B is Valid. and Row="+Integer.toString(row)+" Column="+Integer.toString(col), Toast.LENGTH_LONG).show();
                        status.setText("Both Matrices are valid");}*/
                    //DisplayMatrix(utilities.ReadInMatrix(str));
                    output.startAnimation(drop);
                    Save_state();}
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;

            case Constants.Determinant_Matrix:
                try {
                    String str = input.getText().toString();
                    if(utilities.ReadInMatrix(str)!=null)
                    {//utilities.DisplayMatrix(utilities.ReadInMatrix(str));
                   // output.setVisibility(View.VISIBLE);

                    Matrix=utilities.Stringto2DArray_MC(str);
                        Toast.makeText(getApplicationContext(),utilities.Show2DArray_MC(Matrix),Toast.LENGTH_LONG).show();
                     //          float matrix_A[][];
                        RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
                       // DecompositionSolver solver=new LUDecomposition(matrix).getSolver();

                        Double solution=new LUDecomposition(matrix).getDeterminant();
                      //  matrix_A=utilities.ReadInMatrix(str);
                     //   Toast.makeText(getApplicationContext(),utilities.DisplayMatrix(matrix_A),Toast.LENGTH_LONG).show();
                    //str=getString(R.string.determinant);
                    str=String.format(getString(R.string.determinant),solution);
                    //str=str.concat(g)
                    status.setText(str);}
                    //resulta.setText(str);

                    Save_state(); }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Adjoint_Matrix:
                try {
                   // output.setVisibility(View.VISIBLE);
                    String str = input.getText().toString();
                    if (utilities.ReadInMatrix(str)!=null)
                    {
                        float matrix_A[][];
                        matrix_A=utilities.ReadInMatrix(str);
                        matrix_A=utilities.Adjoint(matrix_A);
                        Toast.makeText(getApplicationContext(),"Adjoint:\n"+utilities.DisplayMatrix(matrix_A),Toast.LENGTH_LONG).show();
                        output.setText(utilities.DisplayMatrix(matrix_A));
                        status.setText(getString(R.string.Adjoint));}
                    output.startAnimation(drop);
                    Save_state();}
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Inverse_Matrix:
                try {
                   // output.setVisibility(View.VISIBLE);
                    String str = input.getText().toString();
                    if (utilities.ReadInMatrix(str)!=null)
                    {
                        Matrix=utilities.Stringto2DArray_MC(str);
                        RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
                        //DecompositionSolver solver=new LUDecomposition(matrix).
                        Double solution=new LUDecomposition(matrix).getDeterminant();
                        str=String.format(getString(R.string.determinant),solution);
                        status.setText(str);
                        if(solution==0)
                        {
                            output.setText(getString(R.string.Singular_error));
                        }
                        RealMatrix matrix_inverted=new LUDecomposition(matrix).getSolver().getInverse();
                        Matrix=matrix_inverted.getData();
                        String str_tmp=utilities.Show2DArray_MC(Matrix);
                        Toast.makeText(getApplicationContext(),"Inverse:\n"+str_tmp,Toast.LENGTH_LONG).show();
                        output.setText(str_tmp);
                        status.setText(getString(R.string.Inverse));}
                    output.startAnimation(drop);
                    Save_state();}
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;

            case Constants.Transpose_Matrix:
                try {
                    //output.setVisibility(View.VISIBLE);
                    String str = input.getText().toString();
                    if (utilities.ReadInMatrix(str)!=null)
                    {  Matrix=utilities.Stringto2DArray_MC(str);
                        RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
                        matrix=matrix.transpose();
                        Matrix=matrix.getData();
                        String str_tmp=utilities.Show2DArray_MC(Matrix);
                        /*float matrix_A[][];
                        matrix_A=utilities.ReadInMatrix(str);
                        matrix_A=utilities.Transpose(matrix_A);*/
                        Toast.makeText(getApplicationContext(),"Transpose:\n"+str_tmp,Toast.LENGTH_LONG).show();
                        output.setText(str_tmp);
                        status.setText(getString(R.string.Transpose));}
                    output.startAnimation(drop);
                    Save_state();}
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Matrix_EigenDecomposition:
                try{
                String str = input.getText().toString();
                if (utilities.ReadInMatrix(str)!=null)
                {  Matrix=utilities.Stringto2DArray_MC(str);
                    RealMatrix matrix=new Array2DRowRealMatrix(Matrix,false);
                    EigenDecomposition ed = new EigenDecomposition(matrix);

                    double[] eigValues = ed.getRealEigenvalues();
                    //RealVector constant = ed.getEigenvector(1);
                    //String tmp1="";
                    //for (int i = 0; i < constant.getDimension(); i++)
                     //   tmp1 += String.format("%d = %.2f\n", i, constant.getEntry(i));


                    String str_tmp=utilities.Show1DArray_MC_InColumn_EGV(eigValues);
                            str_tmp=String.format(getString(R.string.Eigen_value),str_tmp);

                            //Show2DArray_MC(Matrix);
                        /*float matrix_A[][];
                        matrix_A=utilities.ReadInMatrix(str);
                        matrix_A=utilities.Transpose(matrix_A);*/
                    Toast.makeText(getApplicationContext(),str_tmp,Toast.LENGTH_LONG).show();
                    output.setText(str_tmp);
                    //+" and Eigen Vectors are\n"+tmp1);
                    status.setText(getString(R.string.EgenD));
                }
                    output.startAnimation(drop);
                    Save_state();}
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
                return;
            case Constants.Add_Matrices:
                try { Save_state();
                    Intent i=new Intent(Single.this, Add.class);
                   // finish();
                   // if(input.getText().length()>0)
                     //   i.putExtra("value1", input.getText().toString());
                    //if(output.getText().length()>0)
                     //   i.putExtra("value2",output.getText().toString());
                    startActivity(i);
                    //output.setVisibility(View.VISIBLE);
                   // String str1 = input.getText().toString();
                    //String str2= output.getText().toString();
                    //if((utilities.ReadInMatrix(str1)!=null)&&(utilities.ReadInMatrix(str2)!=null))
                    //{
//                        DisplayMatrix(AddMatrix(utilities.ReadInMatrix(str1), (utilities.ReadInMatrix(str2))));
                      //  status.setText(getString(R.string.Sum));}
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Multiply_Matrices:
                try { Save_state();
                    Intent i=new Intent(Single.this, Add.class);
                   // finish();
                    startActivity(i);
                    //output.setVisibility(View.VISIBLE);
                    //String str1 = input.getText().toString();
                    //String str2= output.getText().toString();
                    //if((utilities.ReadInMatrix(str1)!=null)&&(utilities.ReadInMatrix(str2)!=null))
                    //{status.setText(getString(R.string.Product));
                      //  DisplayMatrix(MultiplyMatrix(utilities.ReadInMatrix(str1), (utilities.ReadInMatrix(str2))));}
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;

            case Constants.Clear_all:

                output.setText("");
                input.setText("");
                Save_state();
                status.setText(getString(R.string.Opening_status));
               // output.setVisibility(View.INVISIBLE);
                input.startAnimation(drop);
                status.startAnimation(drop);
                resulta.startAnimation(drop);
                output.startAnimation(drop);
                return;

            case Constants.Save_Matrices:
                if((Gather_box_data()!=null)&& (utilities.getSdcard()!=null))
                {
                    String dir_name=utilities.getSdcard();
                    String message=String.format("Enter text file name to save[placed in Sd card: %s ]", dir_name);
                    //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    Input_box(message, "Matrix1.txt", Gather_box_data(), Constants.Save_Matrices);
                    Save_state();
                }

                return;

            case Constants.Copy_Matrix:

                if(Gather_box_data()!=null)
                {utilities.Copy_Clip(Gather_box_data(),Single.this);
                    status.setText("Copied data from Clipboard into Matrices [A] and [B]/Output");}
                Save_state();
                return;

            case Constants.Paste_Matrix:
                Save_state();
                if(utilities.paste_me(Single.this)!=null)
                {input.setText(utilities.paste_me(Single.this));
                    status.setText("Pasted Clipboard data into Matrix [A] Please Validate Matrix by [??]  button");}
                Save_state();
                return;
            case Constants.Share_Matrices:
                try { Save_state();
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/*");
                    //share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                    // Add data to the intent, the receiving app will decide
                    // what to do with it.
                    share.putExtra(Intent.EXTRA_SUBJECT, "Result");



                    if(Gather_box_data()!=null)
                        share.putExtra(Intent.EXTRA_TEXT, Gather_box_data());
                    Save_state();
                    startActivity(Intent.createChooser(share, "Share Result of Computation..."));
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Save_Matrices_As_Pdf:
                output.startAnimation(drop);
                input.startAnimation(drop);
                if((Gather_box_data()!=null)&& (utilities.getSdcard()!=null))
                { Save_state();
					String css_stored=".ColorTable {	text-align: center;margin:0px;padding:0px;	width:100%;	box-shadow: 10px 10px 5px #888888;	border:1px solid #000000;		-moz-border-radius-bottomleft:0px;	-webkit-border-bottom-left-radius:0px;	border-bottom-left-radius:0px;		-moz-border-radius-bottomright:0px;	-webkit-border-bottom-right-radius:0px;	border-bottom-right-radius:0px;		-moz-border-radius-topright:0px;	-webkit-border-top-right-radius:0px;	border-top-right-radius:0px;		-moz-border-radius-topleft:0px;	-webkit-border-top-left-radius:0px;	border-top-left-radius:0px;}.ColorTable table{   background-color:#aad4ff; border-collapse: collapse;        border-spacing: 0;	width:100%;	height:100%;	margin:0px;padding:0px;}.ColorTable tr:last-child td:last-child {	-moz-border-radius-bottomright:0px;	-webkit-border-bottom-right-radius:0px;	border-bottom-right-radius:0px;}.ColorTable table tr:first-child td:first-child {	-moz-border-radius-topleft:0px;	-webkit-border-top-left-radius:0px;	border-top-left-radius:0px;}.ColorTable table tr:first-child td:last-child {	-moz-border-radius-topright:0px;	-webkit-border-top-right-radius:0px;	border-top-right-radius:0px;}.ColorTable tr:last-child td:first-child{	-moz-border-radius-bottomleft:0px;	-webkit-border-bottom-left-radius:0px;	border-bottom-left-radius:0px;}.ColorTable tr:hover td{	}.ColorTable tr:nth-child(odd){ background-color:#aad4ff; }.ColorTable tr:nth-child(even)    { background-color:#ffffff; }.ColorTable td{	vertical-align:middle;			border:1px solid #000000;	border-width:0px 1px 1px 0px;	text-align:left;	padding:7px;	font-size:10px;	font-family:Arial;	font-weight:normal;	color:#000000;}.ColorTable tr:last-child td{	border-width:0px 1px 0px 0px;}.ColorTable tr td:last-child{	border-width:0px 0px 1px 0px;}.ColorTable tr:last-child td:last-child{	border-width:0px 0px 0px 0px;}.ColorTable tr:first-child td{		background:-o-linear-gradient(bottom, #005fbf 5%, #003f7f 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #005fbf), color-stop(1, #003f7f) );	background:-moz-linear-gradient( center top, #005fbf 5%, #003f7f 100% );	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=\"#005fbf\", endColorstr=\"#003f7f\");	background: -o-linear-gradient(top,#005fbf,003f7f);	background-color:#005fbf;	border:0px solid #000000;	text-align:center;	border-width:0px 0px 1px 1px;	font-size:14px;	font-family:Arial;	font-weight:bold;	color:#ffffff;}.ColorTable tr:first-child:hover td{	background:-o-linear-gradient(bottom, #005fbf 5%, #003f7f 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #005fbf), color-stop(1, #003f7f) );	background:-moz-linear-gradient( center top, #005fbf 5%, #003f7f 100% );	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=\"#005fbf\", endColorstr=\"#003f7f\");	background: -o-linear-gradient(top,#005fbf,003f7f);	background-color:#005fbf;}.ColorTable tr:first-child td:first-child{	border-width:0px 0px 1px 0px;}.ColorTable tr:first-child td:last-child{	border-width:0px 0px 1px 1px;}";
                    String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>    <title>Hello Document</title>"+
					"<style type=\"text/css\">"+css_stored+
					"</style></head><body><h1 style=\"text-align: center\">";

                    String tmp=input.getText().toString().trim();
                            tmp=tmp.replaceAll("\n", "</td></tr><tr><td>");
                            tmp=tmp.replaceAll("  ","</td><td>");
                            tmp=tmp.replaceAll(" ","</td><td>");
							tmp="<table class=\"ColorTable\"><tr><td>"+tmp+"</td></tr></table>";

                            str=str+status.getText().toString().trim();
                    str+="</h1><br/><br/>";
                    str=str+tmp+"<p></p>";
                    if(resulta.getText().length()>0)
                    {    str+= "<p></p><h1>"+resulta.getText().toString()+"</h1><p></p>";}
                    if (output.getText().toString().length() > 0)
                    {
                        tmp=output.getText().toString().trim();
                        tmp = tmp.replaceAll("\n", "</td></tr><tr><td>");
                            tmp=tmp.replaceAll("  ","</td><td>");
                            tmp=tmp.replaceAll(" ","</td><td>");
							tmp="<table class=\"ColorTable\"><tr><td>"+tmp+"</td></tr></table>";
                        str+= "<hr/><p><u>&nbsp;&nbsp;&nbsp;Result&nbsp;&nbsp;&nbsp;</u></p><hr/>" + tmp;}

                                str+="<p></p></body></html>";
                    String dir_name=utilities.getSdcard();
                    String message=String.format("Enter Html file name to save[placed in Sd card: %s ]", dir_name);
                    //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                    Input_box(message, "matrix1.htm", str, Constants.Save_Matrices);

                }
                /*try{
                    PDDocument document = new PDDocument();
                    TextToPDF textToPDF=new TextToPDF();
                    Reader reader=new FileReader(utilities.getSdcard()+File.separator+"Matrix1.txt");
                    textToPDF.createPDFFromText(document, reader);
                    document.close();
                    document.save(utilities.getSdcard()+File.separator+"Hello World.pdf");

                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }*/
                //  try{
                // Create a document and add a page to it
                /*PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage( page );
// Create a new font object selecting one of the PDF base fonts
                   // "file:///android_asset/betty.png"
                    //File file=new File("\"file:///android_asset/dozer.ttf");
                    PDFont font = PDTrueTypeFont.loadTTF(document,getAssets().open("DejaVuSans.ttf"));//file);


                            //document,"file:///android_asset/dozer.ttf");
                    //Toast.makeText(getApplicationContext(),getAssets().open("dozer.ttf").toString(),Toast.LENGTH_LONG).show();
                    //PDFont font=PDType1Font.
                //PDFont font = PDType1Font.HELVETICA_BOLD;;
                    //getAssets().
// Start a new content stream which will "hold" the to be created content
                PDPageContentStream contentStream = new PDPageContentStream(document, page);;
// Define a text content stream using the selected font, moving the cursor and drawing       the text "Hello World"
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.moveTextPositionByAmount(100, 700);
                    //contentStream.newLine();
                contentStream.showText("hello");
                    contentStream.newLine();
                    contentStream.moveTextPositionByAmount(120, 720);
                    contentStream.showText("hello uuu");
                    contentStream.newLine();


                    String str=Gather_box_data();
                    str=str.replaceAll("\n"," ");
                    contentStream.showText(str);
                    contentStream.drawString(str);

                contentStream.endText();
// Make sure that the content stream is closed:
                contentStream.close();
// Save the results and ensure that the document is properly closed:
                document.save(utilities.getSdcard()+File.separator+"Hello.pdf");
                document.close();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }*/
                //==TODO==

                return;

            case Constants.Cramer_Matrix:
                try { Save_state();
                    Intent i=new Intent(Single.this, CR.class);
                    // finish();
                   // if(input.getText().length()>0)
                     //   i.putExtra("value1", input.getText().toString());

                    startActivity(i);
                    //output.setVisibility(View.VISIBLE);
                    // String str1 = input.getText().toString();
                    //String str2= output.getText().toString();
                    //if((utilities.ReadInMatrix(str1)!=null)&&(utilities.ReadInMatrix(str2)!=null))
                    //{
//                        DisplayMatrix(AddMatrix(utilities.ReadInMatrix(str1), (utilities.ReadInMatrix(str2))));
                    //  status.setText(getString(R.string.Sum));}
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;
            case Constants.Help:
                try { Save_state();
                    Intent i=new Intent(Single.this, Help.class);
                    // finish();
                    // if(input.getText().length()>0)
                    //   i.putExtra("value1", input.getText().toString());

                    startActivity(i);
                    //output.setVisibility(View.VISIBLE);
                    // String str1 = input.getText().toString();
                    //String str2= output.getText().toString();
                    //if((utilities.ReadInMatrix(str1)!=null)&&(utilities.ReadInMatrix(str2)!=null))
                    //{
//                        DisplayMatrix(AddMatrix(utilities.ReadInMatrix(str1), (utilities.ReadInMatrix(str2))));
                    //  status.setText(getString(R.string.Sum));}
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return;



            //}

        }}
    public String Gather_box_data(){
        String tmp = "";

        if(status.getText().length()>0)
            tmp=status.getText().toString();

        if (input.getText().toString().length() > 0)
            tmp = tmp+"\n"+"["+input.getText().toString()+"]";
        else
            return null;

        if(resulta.getText().length()>0)
            tmp=tmp+ "\n"+resulta.getText().toString();


        if (output.getText().toString().length() > 0)
            tmp = tmp + "\n========Result==============\n" + "["+output.getText().toString()+"]"+"\n";

        return tmp;

    }
    public void  Click_handler(View view){
        if(view!=null)
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        switch (view.getId())
        {
            case R.id.button1:
                Actions_do(Constants.Validate_Matrix);
                return;
            case R.id.button2:
                Actions_do(Constants.Determinant_Matrix);
                return;
            case R.id.button3:
                Actions_do(Constants.Matrix_EigenDecomposition);
                //Actions_do(Constants.Adjoint_Matrix);
                return;
            case R.id.button4:
                PopupMenu popupMenu0 = new PopupMenu(Single.this, view) {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_inverse1:
                                Actions_do(Constants.Inverse_Matrix);
                                return true;
                            case R.id.nav_adjoint1:
                                Actions_do(Constants.Adjoint_Matrix);
                                return true;
                            case R.id.nav_transpose1:
                                Actions_do(Constants.Transpose_Matrix);
                                return true;
                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu0.inflate(R.menu.menu_inverse_matrix);
                popupMenu0.show();

                return;
               // Actions_do(Constants.Inverse_Matrix);

            case R.id.button5:
                Actions_do(Constants.Clear_all);
                //Actions_do(Constants.Traspose_Matrix);
                return;
            case R.id.button6:
                Actions_do(Constants.Add_Matrices);
                return;
            case R.id.button7:
                PopupMenu popupMenu = new PopupMenu(Single.this, view) {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_share:
                                Actions_do(Constants.Share_Matrices);
                                return true;
                            case R.id.nav_save:
                                Actions_do(Constants.Save_Matrices);
                                return true;
                            case R.id.nav_save_as_PDF:
                                Actions_do(Constants.Save_Matrices_As_Pdf);
                                return true;
                            case R.id.nav_copy:
                                Actions_do(Constants.Copy_Matrix);
                                return true;
                            case R.id.nav_paste:
                                Actions_do(Constants.Paste_Matrix);
                                return true;
                            case R.id.nav_clear_all:
                                Actions_do(Constants.Clear_all);
                                return true;
                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu.inflate(R.menu.popup);
                popupMenu.show();

                return;
            case R.id.button8:
                Actions_do(Constants.Cramer_Matrix);
                return;
        }

    }


    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.a, menu);
          return true;
      }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_exit:
                Save_state();
                finish();
                System.exit(0);
                break;
            case R.id.nav_validate:
                Actions_do(Constants.Validate_Matrix);
                break;
            case R.id.nav_determinant:
                Actions_do(Constants.Determinant_Matrix);
                break;
            case R.id.nav_adjoint:
                Actions_do(Constants.Adjoint_Matrix);
                break;
            case R.id.nav_inverse:
                Actions_do(Constants.Inverse_Matrix);
                break;
            case R.id.nav_transpose:
                Actions_do(Constants.Transpose_Matrix);
                break;
            case R.id.nav_add:
                Actions_do(Constants.Add_Matrices);
                break;

            case R.id.nav_share:
                Actions_do(Constants.Share_Matrices);
                break;
            case R.id.nav_save:
                Actions_do(Constants.Save_Matrices);
                break;
            case R.id.nav_save_as_PDF:
                Actions_do(Constants.Save_Matrices_As_Pdf);
                break;
            case R.id.nav_copy:
                Actions_do(Constants.Copy_Matrix);
                break;
            case R.id.nav_paste:
                Actions_do(Constants.Paste_Matrix);
                break;
            case R.id.nav_clear_all:
                Actions_do(Constants.Clear_all);
                break;
            case R.id.nav_cramers_rule:
                Actions_do(Constants.Cramer_Matrix);
                break;
            case R.id.nav_help:
                Actions_do(Constants.Help);
                break;
            case R.id.nav_EigenDecomposition:
                Actions_do(Constants.Matrix_EigenDecomposition);
                break;





        }
        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else*//* if (id == R.id.nav_exit) {


        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public Boolean Input_box(String message,String default_text, final String contenta, final int action){
        try { Save_state();
            //Set an EditText view to get user input
            final EditText input = new EditText(Single.this);
            input.setText(default_text);
//final String editable;
            new AlertDialog.Builder(Single.this)
                    .setTitle("File")
                    .setMessage(message)
                    .setView(input)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String editable = input.getText().toString();
                            if(editable.length()>0){
                                switch (action){
                                    case Constants.Save_Matrices:
                                        try {

                                            File f=new File(utilities.getSdcard()+File.separator+editable);
                                            if (utilities.storeStringinFile(contenta, f))
                                            Toast.makeText(getApplicationContext(),"Success, Saved :\n"+f.getPath(),Toast.LENGTH_LONG).show();


                                        }

                                        catch (Exception e){
                                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                                        }
                                        return;
                                }
                                // deal with the editable
                            }
                        }})

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            // Do nothing.
                        }
                    }).show();
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Save_state();
    }
}








