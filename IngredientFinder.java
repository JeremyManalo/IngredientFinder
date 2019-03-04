import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

class IngredientFinder
{
    public static void main(String args[])
    {
        IngredientFinder run = new IngredientFinder();
        String[] pictureFiles =
        {
          "Images/IngrList-1(0).png",
          "Images/IngrList-2(0).png",
          "Images/IngrList-3(0).png" 
        };
        BufferedImage[] pictures = new BufferedImage[pictureFiles.length];
        for (int i = 0; i < pictures.length; i++)
            pictures[i] = run.importImage(new File(pictureFiles[i]), 1000, 1000);

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
}
