package ManipularCsv;

public class MyEntity {
    private int field1;
    private String field2;
    private int field3;
    private int field4;
    private char field5;
    private int field6;
    private boolean field7;

    public MyEntity(int field1, String field2, int field3, char field5, int field4, int field6, boolean field7) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field5 = field5;
        this.field4 = field4;
        this.field6 = field6;
        this.field7 = field7;
    }
    public MyEntity(){

    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "Cedula=" + field1 + '\'' +
                ", Nombre='" + field2 + '\'' +
                ", NSemanas=" + field3 + '\'' +
                ", Cotización=" + field4 +'\'' +
                ", Categoria=" + field5 + '\'' +
                ", Edad=" + field6 + '\''+
                ", Embargo=" + field7 + '\'' +
                '}';
    }



    //-----------------------------------------------------------------------------------------------------------
    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    public int getField4() {
        return field4;
    }

    public void setField4(int field4) {
        this.field4 = field4;
    }

    public char getField5() {
        return field5;
    }

    public void setField5(char field5) {
        this.field5 = field5;
    }

    public int getField6() {
        return field6;
    }

    public void setField6(int field6) {
        this.field6 = field6;
    }

    public boolean isField7() {
        return field7;
    }

    public void setField7(boolean field7) {
        this.field7 = field7;
    }
}


