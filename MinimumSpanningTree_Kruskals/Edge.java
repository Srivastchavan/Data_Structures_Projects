public class Edge implements Comparable<Edge>
	{
		int distance;
		String vertex1 , vertex2;
		
		Edge(String arg1, String arg2, int dist)
		{
			this.vertex1 = arg1;
			this.vertex2 = arg2;
			this.distance = dist;
		}
		public int compareTo(Edge e)
		{
			if (this.distance < e.distance)
				return -1;
			else if (this.distance > e.distance)
				return 1;
			
			else 
				return 0;
		}
	}