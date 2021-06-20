import java.awt.image.*;
import java.awt.Color;
import javax.imageio.*;
import java.io.*;
import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		// i am certain there is a better way to do this
		BufferedImage source_img = null;
		BufferedImage img_to_superimpose = null;
		int new_width;
		int new_height;
		Scanner in = new Scanner(System.in);
		System.out.println("Please input source image filename: ");
		String filename = in.nextLine();
		try {
			source_img = ImageIO.read(new File (filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Please input chroma-keyed image filename: ");
		filename = in.nextLine();
		try {
			img_to_superimpose = ImageIO.read(new File (filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img_to_superimpose.getWidth() > source_img.getWidth()) {
			new_width = img_to_superimpose.getWidth();
		}
		else {
			new_width = source_img.getWidth();
		}
		if (img_to_superimpose.getHeight() > source_img.getHeight()) {
			new_height = img_to_superimpose.getHeight();
		}
		else {
			new_height = source_img.getHeight();
		}
		BufferedImage composite_image = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);

		System.out.println("Source width is " + new_width + ", height is " + new_height);

		// write our source image to the new image...
		for (int x_coord = 0; x_coord < new_width; x_coord++) {
			for (int y_coord = 0; y_coord < new_height; y_coord++) {
				Color source_rgb = new Color(source_img.getRGB(x_coord, y_coord));
				Color rgb_to_superimpose = new Color(img_to_superimpose.getRGB(x_coord, y_coord));
				if (rgb_to_superimpose.getRed() <= 100 && rgb_to_superimpose.getGreen() > 200 && rgb_to_superimpose.getBlue() <= 100) {
					composite_image.setRGB(x_coord, y_coord, source_rgb.getRGB());
				}
				else {
					composite_image.setRGB(x_coord, y_coord, rgb_to_superimpose.getRGB());
				}
			}
		}

		try{
			File outputfile = new File("output.png");
			ImageIO.write(composite_image, "png", outputfile);
			System.out.println("Image composition complete! Sent to output.png.");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}