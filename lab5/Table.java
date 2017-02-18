/**
 * Created by wsnedaker on 2/17/2017.
 */
public class Table{
    public String name;
    public String[][] table;
    public Integer numrows = 0;
    public Integer numcols = 0;
    public Integer stringsize = 0;


    public Table(String name,String[] stra){
        this.name = name;
        numcols = stra.length;
        stringsize = numcols;
        this.table = new String[stra.length][stra.length];
        for(int in = 0; in < stra.length; in += 1){
            this.table[in][0] = stra[in];

        }
        numrows += 1;
    }
    public void addRow(String[] atr){
        if (numrows == stringsize){
            this.resize(numrows*2);
        }
        for( int i = 0; i < atr.length; i += 1){
            this.table[i][numrows] = atr[i];
        }

    }
    private void resize(int size){
        String[][] newb = new String[numcols][size];
        for( int i = 0; i < numrows; i ++){
            for (int y = 0; y < numcols; y ++){
                newb[y][i] = this.table[y][i];
            }
        }
        this.table = newb;
    }
    public static void main(String [ ] args){
        String[] str = new String[2];
        str[0] = "x";
        str[1] = "y";
        Table T1;
        T1 = new Table("T1", str);
        String[] str1 = new String[2];
        str1[0] = "2";
        str1[1] = "5";
        String[] str2 = new String[2];
        str2[0] = "8";
        str2[1] = "3";
        String[] str3 = new String[2];
        str3[0] = "13";
        str3[1] = "7";
        T1.addRow(str1);
        T1.addRow(str2);
        T1.addRow(str3);


    }
}
