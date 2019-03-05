import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import java.util.ArrayList;

class IngredientFinder
{
    public static void main(String args[])
    {
        IngredientFinder run = new IngredientFinder();

        run.deleteFolder(new File("ParsedImages"));
        File ParsedImagesFiles = new File("ParsedImages");
        ParsedImagesFiles.mkdir();

        File ImagesFolder = new File("Images");
        File[] pictureFiles = ImagesFolder.listFiles();

        BufferedImage[] pictures = new BufferedImage[pictureFiles.length];

        for (int i = 0; i < pictures.length; i++)
        {
            pictures[i] = run.importImage(pictureFiles[i], 500, 500);
            int[][] picturePixels = run.convertTo2DWithoutUsingGetRGB(pictures[i]);
            run.writePixelsToFile(picturePixels, i + 1);
        }
    }

    BufferedImage importImage(File file, int width, int height)
    {
        BufferedImage image = null;
        try
        {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            System.out.println("Reading complete.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }

        return image;
    }

    //method by Motasim: https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength)
            {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width)
                {
                    col = 0;
                    row++;
                }
            }
        }
        else
        {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength)
            {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width)
                {
                   col = 0;
                   row++;
                }
            }
        }
        return result;
   }

    void writePixelsToFile(int[][] picturePixels, int pictureNumber)
    {
        PrintWriter writer = null;
        try
        {
            File file = new File("ParsedImages", "parsedPicture-" + pictureNumber + ".txt");
            writer = new PrintWriter("ParsedImages/parsedPicture-" + pictureNumber + ".txt", "UTF-8");

            for (int i = 0; i < picturePixels.length; i++)
            {
                for (int j = 0; j < picturePixels[0].length; j++)
                {
                    if (picturePixels[i][j] > -5000000)
                        writer.print(".");
                    else
                        writer.print("0");
                    // writer.print(picturePixels[i][j]);
                }
                writer.println();
            }
            writer.close();
            System.out.println("Picture Parsed.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
    }

    //method by NCode: https://stackoverflow.com/questions/7768071/how-to-delete-directory-content-in-java
    public static void deleteFolder(File folder)
    {
        File[] files = folder.listFiles();
        if(files!=null)
        { //some JVMs return null for empty dirs
            for(File f: files)
            {
                if(f.isDirectory())
                    deleteFolder(f);
                else
                    f.delete();
            }
        }
        folder.delete();
    }
}
