package jungtest;
import java.awt.*;  
import java.io.BufferedReader;
import java.io.*;

import javax.swing.*;  

import org.apache.commons.collections15.Transformer;  
 

//http://faithlee.iteye.com/blog/1420065  
import edu.uci.ics.jung.graph.*;  
import edu.uci.ics.jung.graph.util.EdgeType;  
import edu.uci.ics.jung.algorithms.layout.*;  
import edu.uci.ics.jung.visualization.BasicVisualizationServer;  
import edu.uci.ics.jung.visualization.VisualizationViewer;  
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;  
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;  
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;  


public class jungtest extends JFrame {



    @SuppressWarnings("unchecked")  
    public jungtest() {  

        
		
    	
    	
    	
        this.setTitle("示例");  
        this.setFont(new Font("Times New Roman", Font.PLAIN, 12));  
        this.setBackground(Color.white);// 设置窗口背景颜色  
        // 初始化图g  
        SparseGraph g = new SparseGraph();  
        
        for(int i =0;i<230;i++){
        	g.addVertex(actor[i]);
        	
        }
        System.out.println("node ok");
        for(int i = 0; i<3854;i++){
        	System.out.println(data[i][1]);
        	g.addEdge(i, data[i][0], data[i][1],EdgeType.UNDIRECTED); 
        }
  
        /*for (int i = 1; i < 10; i++) {  
            g.addVertex(i);  
            g.addEdge("Edge[1," + (i + 1) + "]", 1, i + 1);  
            if (i > 1) {  
                g.addEdge("Edge[" + i + "," + (i + 1) + "]", i, i + 1);  
            }  
        }  */
 //http://faithlee.iteye.com/blog/1420065  
        System.out.println("The graph g = " + g.toString());  
        // 使用该图创建布局对象  
        // Layout layout = new CircleLayout(g);//圆形布局结构  
        Layout layout = new FRLayout2(g);  
        // 使用布局对象创建BasicVisualizationServer对象  
        // BasicVisualizationServer vv = new BasicVisualizationServer(layout);  
        VisualizationViewer<Integer, Paint> vv = new VisualizationViewer<Integer, Paint>(  
                layout);  
  
        // 渲染环境 颜色参数  
        Transformer<Integer, Paint> vertexPaint = new Transformer<Integer, Paint>() {  
            public Paint transform(Integer s) {  
                if (s == 1)  
                    return Color.green;  
                else  
                    return Color.YELLOW;  
            }  
  
        };  
  
        // 设置顶点文本标签  
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());  
        // 设置顶点颜色  
        // vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);  
        // 设置边的文本标签  
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());  
        // 设置边的线型  
        // vv.getRenderContext().setEdgeStrokeTransformer();  
  
        DefaultModalGraphMouse<Integer, Paint> gm = new DefaultModalGraphMouse<Integer, Paint>();  
        gm.setMode(Mode.PICKING);  
        vv.setGraphMouse(gm);  
        // 将上述对象放置在一个Swing容器中并显示之  
        getContentPane().add(vv);  
        pack();  
    }  
    public static String[][] data = new String[3854][4];
    public static String[] actor = new String[230];
    public static void main(String[] args) throws IOException, Exception{  
    	FileReader fr = new FileReader("天龍八部角色關係權重.csv");
		BufferedReader br = new BufferedReader(fr);
		int count = 0;
		while (br.ready()) {
			String line= br.readLine();
			String[] linesp =line.split(",");
			System.out.println(line);
			if(count!=0){
				data[count-1][0] = linesp[0];
				data[count-1][1] = linesp[1];
				data[count-1][2] = linesp[2];
				data[count-1][3] = linesp[3];
			}
			count++;
		}
		br.close();
		
		FileReader fr1 = new FileReader("actor_角色不重複_別名_空白隔開.txt");
		BufferedReader br1 = new BufferedReader(fr1);
		int count1 = 0;
		while (br1.ready()) {
			actor[count1]= br1.readLine();

			count1++;
		}
		br1.close();
		
		
    	jungtest myframe = new jungtest();  
        myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        myframe.setVisible(true);  
    }  
}  