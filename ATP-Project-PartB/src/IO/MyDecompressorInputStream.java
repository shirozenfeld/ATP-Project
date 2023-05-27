package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    public MyDecompressorInputStream(InputStream inp)
    {
        this.in=inp;
    }
    @Override
    public int read() throws IOException
    {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException
    {

        int offset = 0;

        for (int i=0; i<24; i++) {
            b[offset] = intToByte(read());
            offset++;
        }

        //int bytesValue = 0;
        try {
            //bytesValue = read();
            //while ( bytesValue != -1)
            while(true)
            {
                byte[] readBytes = new byte[4];
                if(in.read(readBytes, 0, 4) == -1){
                    break;
                };
                int readValue = ((readBytes[0] & 0xFF) << 24) |
                        ((readBytes[1] & 0xFF) << 16) |
                        ((readBytes[2] & 0xFF) << 8) |
                        (readBytes[3] & 0xFF);
                byte[] curr32cells = intToByteArray(readValue, 32);
                System.arraycopy(curr32cells, 0, b, offset, 32);
                offset+=32;
                /*byte[] curr32cells = intToByteArray(bytesValue, 32);
                System.arraycopy(curr32cells, 0, b, offset, 32);
                offset+=32;
                bytesValue = read();*/
                // Reading the integer value from the input stream

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public byte[] intToByteArray(int number, int size) {
        byte[] byteArray = new byte[size];

        for (int i = size-1; i >= 0; i--) {
            byteArray[i] = (byte) (number & 0x01);
            number >>= 1;
        }

        return byteArray;
    }
    public byte intToByte(int number) {
        return (byte) number;
    }

}