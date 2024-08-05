package com.uees;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
public class GraphLA<E> {
    private LinkedList<Vertex<E>> vertexes;
    private boolean directed;

    public GraphLA(boolean directed) {
        this.directed = directed;
        this.vertexes = new LinkedList<>();
    }

        public LinkedList<Vertex<E>> pathFindertoVertex(E data) {
            LinkedList<Vertex<E>> path = new LinkedList<>();
            Vertex<E> vertex = searchVertex(data);
            if (vertex != null) {
                while (vertex != null) {
                    path.addFirst(vertex);
                    vertex = vertex.getAntecesor();
                }
            }
            return path;
        }
        
    

    public boolean addVertex(E data)
    {
        if(data == null) return false;
        if(searchVertex(data)!=null) return false;
        Vertex<E> v = new Vertex<>(data);
        return vertexes.add(v);
    }

    public boolean addEdge(E src, E dst, int weight)
    {
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(vs,vd,weight);
        vs.getEdges().add(e);
        if(!directed)
        {
            Edge<E> e1 = new Edge<>(vd, vs, weight);
            vd.getEdges().add(e1);
        }
        return false;
    }

    private Vertex<E> searchVertex(E data)
    {
        for(Vertex<E> v : vertexes)
        {
            if(v.getData().equals(data))
                return v;
        }
        return null;
    }

    public String toString()
    {
        if(this.vertexes.isEmpty()) return "V={}\nA={}";
        StringBuilder sb = new StringBuilder();
        sb.append("V={");
        for(Vertex<E> v : this.vertexes)
        {
            sb.append("(");
            sb.append(v.getData());
            sb.append(",");
            sb.append(v.getDistancia());
            sb.append(",");
            if(v.getAntecesor()!=null)
                sb.append(v.getAntecesor().getData());
            sb.append("),");
        }
        sb.append("}\nA={");
        for(Vertex<E> v : this.vertexes)
        {
            for(Edge<E> e : v.getEdges())
            {
                sb.append("(");
                sb.append(e.getSrc().getData());
                sb.append(",");
                sb.append(e.getDst().getData());
                sb.append(",");
                sb.append(e.getWeight());
                sb.append("),");
            }
        }
        sb.append("}");
        return sb.toString();
    }
    public LinkedList<E> dfs(E src)
    {
        LinkedList<E> res = new LinkedList<>();
        Stack<Vertex<E>> pila = new Stack<>();
        Vertex<E> v = searchVertex(src);
        if(v == null) return res;
        v.setVisited(true);
        pila.push(v);
        while(!pila.isEmpty())
        {
            Vertex<E> v1 = pila.pop();
            res.add(v1.getData());
            for(Edge<E> e : v1.getEdges())
            {
                if(!e.getDst().isVisited())
                {
                    e.getDst().setVisited(true);
                    pila.push(e.getDst());
                }
            }
        }
        for(Vertex<E> v1 : this.vertexes)
        {
            v1.setVisited(false);
        }
        return res;
    }

    public void dijkstra(E src)
    {
        for(Vertex<E> v : this.vertexes)
        {
            v.setDistancia(Integer.MAX_VALUE);
            v.setAntecesor(null);
            v.setVisited(false);
        }
        Vertex<E> v = searchVertex(src);
        v.setDistancia(0);
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2)->Integer.compare(v1.getDistancia(),v2.getDistancia()));
        cola.offer(v);
        while(!cola.isEmpty())
        {
            Vertex<E> v1 = cola.poll();
            v1.setVisited(true);
            for(Edge<E> e : v1.getEdges())
            {
                if(!e.getDst().isVisited())
                {
                    if(v1.getDistancia()+e.getWeight()<e.getDst().getDistancia())
                    {
                        e.getDst().setDistancia(v1.getDistancia()+e.getWeight());
                        e.getDst().setAntecesor(v1);
                        cola.offer(e.getDst());
                    }
                }
            }
        }
    }
}
