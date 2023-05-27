package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream
{
    private InputStream in;
    public SimpleDecompressorInputStream(InputStream inp)
    {
        this.in=inp;
    }
    @Override
    public int read()
    {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException
    {
        int counter =0 ;
        int offest = 0;

        for (int i=0; i<24; i++) {
            b[offest] = intToByte(this.in.read());
            offest++;
        }


        //fill the given array with the input's values
        int bytesValue; //0-255
        int curr=0;
        try {
            while ((bytesValue = this.in.read()) != -1) {
                counter++;
                byte[] currByteArray = new byte[bytesValue];
                if (curr == 0) {
                    for (int i = 0; i < bytesValue; i++) {
                        currByteArray[i] = (byte) 0;
                    }
                    System.arraycopy(currByteArray, 0, b, offest, bytesValue);
                    offest += bytesValue;
                    curr = 1;
                } else {
                    for (int i = 0; i < bytesValue; i++) {
                        currByteArray[i] = (byte) 1;
                    }
                    System.arraycopy(currByteArray, 0, b, offest, bytesValue);
                    offest += bytesValue;
                    curr = 0;
                }
            }

            return counter;
        }
        catch (Exception e)
        {
            return -1;
        }
    }


    private byte[] intToByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (value >> 24);
        byteArray[1] = (byte) (value >> 16);
        byteArray[2] = (byte) (value >> 8);
        byteArray[3] = (byte) value;
        return byteArray;
    }

    public String intToBinaryString(int value) {
        StringBuilder sb = new StringBuilder(8);

        // Iterate over each bit in the integer, starting from the most significant bit
        for (int bit = 7; bit >= 0; bit--) {
            // Extract the bit value (0 or 1) from the integer using bitwise shift and AND operator
            int bitValue = (value >> bit) & 0x01;
            sb.append(bitValue);
        }

        return sb.toString();
    }

    public byte intToByte(int number) {
        return (byte) number;
    }





}
