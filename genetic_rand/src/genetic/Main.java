package genetic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

import genetic.csv.*;
import genetic.data.*;
import genetic.mutation.*;
import genetic.gene.*;

public class Main
{
	static DistanceTable distanceTable;

	private static class cmp implements Comparator<Gene>
	{
		public int compare(Gene g1, Gene g2)
		{
			Integer i1 = new Integer(g1.getTotalNextDistance());
			return i1.compareTo(new Integer(g2.getTotalNextDistance()));
		}
	}

	public static void main(String[] args) throws Exception
	{
		ClassSystem system = new ClassSystem(args[0]);
		system.selectSemester(2013, 1);

		Gene gene = ClassManager.getInstance().makeGene();


		for(int t_c = 0; t_c<10; t_c++)
		{
			Gene[] parent = new Gene[100];

			parent[0] = gene;

			for(int i=1; i<parent.length/10; i++)
			{
				RandomArrayAdaptor rA = new RandomArrayAdaptor();
				rA.setGene(parent[0]);
				parent[i] = rA.mutate(100);
				System.out.print("a");
				if(i % 10 == 0)
					System.out.println("");		
			}

			for(int i=parent.length/10; i<parent.length/2; i++)
			{
				RandomArrayAdaptor rA = new RandomArrayAdaptor();
				rA.setGene(parent[0]);

				if(i%4 == 0)
					parent[i] = rA.mutate(10);
				if(i%4 == 1)
					parent[i] = rA.mutate(20);
				if(i%4 == 2)
					parent[i] = rA.mutate(30);
				if(i%4 == 3)
					parent[i] = rA.mutate(40);

				System.out.print("r");
				if(i % 10 == 0)
					System.out.println("");		
			}

			for(int i=parent.length/2; i<parent.length; i++)
			{
				GreedyAdaptor gA = new GreedyAdaptor();
				gA.setGene(gene);
				parent[i] = gA.mutate(10);
				System.out.print("g");
				if(i % 10 == 0)
					System.out.println("");		
			}


			for(int i=0; i<100; i++)
			{
				BufferedWriter out = new BufferedWriter(new FileWriter("./result/result"+t_c+"/generation_1st_"+ i + ".csv"));

				for(int j=0; j<5; j++)
				{
					GreedyAdaptor gA = new GreedyAdaptor();
					gA.setGene(parent[i]);
					System.out.print(j);
					parent[i] = gA.mutate(100);
					String str = j + "," + parent[i].getTotalNextDistance() + ",";
					out.write(str); out.newLine();
					System.out.println(str);
				}

				out.close();
			}


			HashSet<Integer> rd_set = new HashSet<>();
			HashSet<Integer> rd_greedy_set = new HashSet<>();
			Random rd = new Random();


			Gene[] generation = new Gene[100];
			for(int i=0; i<100; i++)
				generation[i] = parent[i];
			for(int count = 0; count < 100; count++)	//new generation
			{
				BufferedWriter out2 = new BufferedWriter(new FileWriter("./result/result"+t_c+"/generation" + (count+1) + ".csv"));
				System.out.println("generation : " + (count + 1));
				rd_set.clear();

				Arrays.sort(generation , new cmp());

				Gene[] tmp = new Gene[100];
				for(int i=0; i<50; i++)
				{
					tmp[i] = generation[i];
					rd_set.add(i);
				}

				for(int i=50; i<100; i++)
				{
					RandomArrayAdaptor rA = new RandomArrayAdaptor();
					rA.setGene(generation[100-i]);
					tmp[i] = rA.mutate(10);

					System.out.print("r");
					if(i % 10 == 0)
						System.out.println("");		
					rd_set.add(i);
				}

				for(int i=0; i<100; i++)
					generation[i] = tmp[i];


				Arrays.sort(generation, new cmp());
				int t = 0;
				for(Gene _gene : generation)
				{
					String str = ((++t) + "," + _gene.getTotalNextDistance() + ",");
					System.out.println(str);
					out2.write(str); out2.newLine();
				}
				System.out.println("===");
				out2.close();
			}
		}

	}
}
