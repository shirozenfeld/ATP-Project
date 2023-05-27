package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream
{
    public OutputStream out;

    public MyCompressorOutputStream(OutputStream os)
    {
        this.out=os;
    }

    public void write(int b) throws IOException
    {
        this.out.write(b);
    }


    public void write (byte[] b) throws IOException {

//        ByteArrayOutputStream check = new ByteArrayOutputStream();

        for (int i = 0; i < 24; i++)
        {
//            check.write(b[i]);
            write(b[i]);
        }


        int cellsLeft = b.length;
        int offest = 0;

        cellsLeft -=(4*6);
        offest+=(4*6);

        while (cellsLeft >= 32) {
            byte[] currArray = new byte[32];
            System.arraycopy(b, offest, currArray, 0, 32);
            int value = byteArrayToInt(currArray);
            // Writing the integer value as bytes
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (value >> 24);
            bytes[1] = (byte) (value >> 16);
            bytes[2] = (byte) (value >> 8);
            bytes[3] = (byte) value;
            out.write(bytes, 0, bytes.length);
            cellsLeft -=32;
            offest+=32;
            /*byte[] currArray = new byte[32];
            System.arraycopy(b, offest, currArray, 0, 32);
//            check.write(byteArrayToInt(currArray));
            write(byteArrayToInt(currArray));
            cellsLeft -=32;
            offest+=32;*/
        }

//        System.out.println(check.toString());

        if (cellsLeft > 0) {
            byte[] currArray = new byte[cellsLeft];
            System.arraycopy(b, offest, currArray, 0, cellsLeft);
            write(byteArrayToInt(currArray));
        }


    }

    public int byteArrayToInt(byte[] byteArray) {
        int result = 0;

        for (int i = 0; i < byteArray.length; i++) {
            result = (result << 1) | byteArray[i];
        }

        return result;
    }




}