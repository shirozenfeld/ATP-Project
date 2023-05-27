package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream
{
    OutputStream out;
    public SimpleCompressorOutputStream(OutputStream os)
    {
        this.out=os;
    }

    @Override
    public void write(int b) throws IOException
    {
        this.out.write(b);
    }
    @Override
    public void write(byte[] b) throws IOException {
        /**
         * receives a byte array and writes a compressed version of it into the outputstream  object
         */


        for (int i = 0; i < 24; i++)
        {
            write(b[i]);
        }

        //index 6 and further is used for cells values
        int counter=0;
        if (b[24]==(byte)1) {
            out.write(counter);
            counter++;
        }
        else {
            counter++;
        }

        for (int i=25;i<b.length;i++)
        {
            if (b[i-1]==b[i]) {
                if (counter == 255) {
                    out.write(counter); // all sequence
                    out.write(0); // empty sequence
                    counter = 0;

                }
                counter++;
            }
            else
            {
                out.write(counter);
                counter=1;
            }
        }

    }

//    private int byteToInt(byte[] bytes)
//    {
//        int converted=0;
//        for (int i=0;i<4;i++)
//        {
//            converted=(converted<<8) | (bytes[i] & 0xFF);
//        }
//        return converted;
//    }
//
//    public String byteToBinaryString(byte value) {
//        StringBuilder sb = new StringBuilder(8);
//
//        // Iterate over each bit in the byte, starting from the most significant bit
//        for (int bit = 7; bit >= 0; bit--) {
//            // Extract the bit value (0 or 1) from the byte using bitwise shift and AND operator
//            int bitValue = (value >> bit) & 0x01;
//            sb.append(bitValue);
//        }
//
//        return sb.toString();
//    }

}
