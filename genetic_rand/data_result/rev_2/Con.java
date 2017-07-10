import java.io.*;

class Con
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader[] in = new BufferedReader[100];
			BufferedWriter out = new BufferedWriter(new FileWriter("out"));

			for(int j = 0; j < 100; j++)
			{
				in[j] = new BufferedReader(new FileReader("generation_1st_" + j + ".csv"));
			}

			StringBuilder buf = new StringBuilder();
			for(int i=0; i<100; i++)
			{
				for(int j = 0; j<100; j++)
				{
					String str = in[j].readLine();
					String[] tmp = str.split(",");
					buf.append(tmp[1] + ",");
			}
				buf.append("\n");
			}
			out.write(buf.toString());
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
