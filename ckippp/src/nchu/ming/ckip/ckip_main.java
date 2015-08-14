package nchu.ming.ckip;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;

public class ckip_main
{
	public static void main(String[] args) throws IOException
	{
		//創建腳色陣列
		String[] actor = new String[230];
		
		FileReader actor_txt = new FileReader("actor_角色不重複_別名_空白隔開.txt");
		int actor_size = 0;
		Scanner actor_txt_scan = new Scanner(actor_txt);
		
		while(actor_txt_scan.hasNext())
		{
			actor[actor_size] = actor_txt_scan.nextLine();
			actor_size++;
		}
		actor_txt_scan.close();
		actor_txt.close();
		
		//創建角色關係矩陣
		int[][] actor_relationship = new int[230][230];
		for(int i=0;i<actor_relationship.length;i++)
		{
			for(int j=0;j<actor_relationship.length;j++)
			{
				actor_relationship[i][j]=0;
			}
		}
		
		//逐行讀入天龍八部小說
		FileReader tianlong = new FileReader("天龍八部繁UTF8.txt");
		Scanner tianlong_scan = new Scanner(tianlong);
		
		FileWriter tianlong_actor = new FileWriter("天龍八部每句角色.txt");
		BufferedWriter tianlong_actor_scan = new BufferedWriter(tianlong_actor);
		while(tianlong_scan.hasNext())
		{
			ArrayList<Integer> relationship_list = new ArrayList<Integer>();
			String tmp = tianlong_scan.nextLine();
			for(int i=0;i<actor.length;i++)
			{
				String[] actor_split = actor[i].split(" ");
				for(int j=0;j<actor_split.length;j++)
				{
					if(tmp.indexOf(actor_split[j])>-1)
					{
						relationship_list.add(i);
						break;
					}
				}
			}
			for(int i=0;i<relationship_list.size();i++)
			{
				if(relationship_list.size()>1)
				{
					for(int j=0;j<relationship_list.size();j++)
					{
						if(i!=j)
						{
							actor_relationship[relationship_list.get(i)][relationship_list.get(j)]++;
						}
					}
				}
				
				//System.out.print(actor[relationship_list.get(i)]+" ");
				tianlong_actor_scan.write(actor[relationship_list.get(i)]+" ");
			}
			//System.out.println("");
			tianlong_actor_scan.newLine();
		}
		tianlong_scan.close();
		tianlong.close();
		tianlong_actor_scan.close();
		tianlong_actor.close();
		
		//畫圖用權重csv
		FileWriter tianlong_actor_relationship = new FileWriter("天龍八部角色關係權重.csv");
		BufferedWriter tianlong_actor_relationship_scan = new BufferedWriter(tianlong_actor_relationship);
		
		tianlong_actor_relationship_scan.write("Source,Target,Type,Weight");
		tianlong_actor_relationship_scan.newLine();

		for(int i=0;i<actor_relationship.length;i++)
		{
			for(int j=0;j<actor_relationship.length;j++)
			{
				if(actor_relationship[i][j]!=0 && i!=j)
				{
					tianlong_actor_relationship_scan.write(actor[i]+","+actor[j]+",Undirected,"+actor_relationship[i][j]);
					tianlong_actor_relationship_scan.newLine();
				}
			}
		}
		tianlong_actor_relationship_scan.close();
		tianlong_actor_relationship.close();
		
		//分析用點與邊TXT輸出
		FileWriter tianlong_point_edge = new FileWriter("天龍八部點與邊.txt");
		BufferedWriter tianlong_point_edge_scan = new BufferedWriter(tianlong_point_edge);
		for(int i=0;i<actor_relationship.length;i++)
		{
			for(int j=0;j<actor_relationship.length;j++)
			{
				if(actor_relationship[i][j]>0 && i!=j)
				{
					tianlong_point_edge_scan.write((i+1)+" "+(j+1));
					tianlong_point_edge_scan.newLine();
				}
			}
		}
		tianlong_point_edge_scan.close();
		tianlong_point_edge.close();
	}
}