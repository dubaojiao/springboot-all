package com.du;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Title
 * @ClassName MyHashMap
 * @Author jsb_pbk
 * @Date 2019/1/8
 */
public class MyHashMap<K,V>{

   private Node[] nodes;
    private static final Integer SIZE = 1<<4;
    private int size;


    public MyHashMap() {
        this.nodes = new Node[SIZE];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }
    public boolean containsValue(Object value) {
        return false;
    }

    public V get(K key) {
        Node<K,V> node = null;
        //获取hashCode
        Integer hashCode = key.hashCode();
        //计算下标
        Integer index = hashCode % SIZE;
        node = nodes[index];
        if(node == null){
            return null;
        }
        while (node != null){
            if(node.hash.equals(hashCode) && (node.k == key)){
               return node.v;
            }
            node = node.next;
        }
        return null;
    }

    public V put(K key, V value) {
        V oldValue = null;
        //获取hashCode
        Integer hashCode = key.hashCode();
        //计算下标
        Integer index = hashCode % SIZE;
        if(this.nodes == null){
            this.nodes = new Node[SIZE];
        }
        //获取当前下标是否有节点
        if(nodes[index] == null){
            size++;
            nodes[index] = new Node(key,value,null,hashCode);
            return null;
        }

        if(nodes[index].hash.equals(hashCode) && (nodes[index].k == key)){
            // 有节点并且key值相同
            Node<K,V> node = nodes[index];
            while ( node != null){
                if(node.hash.equals(hashCode) && (node.k == key)){
                    oldValue = node.v;
                    node.v = value;
                }
                node = node.next;
            }
        }
        if(oldValue == null){
            size++;
            nodes[index] = new Node(key,value,nodes[index],hashCode);
        }
        return oldValue;

    }

    public V remove(Object key) {
        return null;
    }

    static class Node<K,V>{
        private K k;
        private V v;
        private Node next;
        private Integer hash;

        public Node(K k, V v, Node next,Integer hash) {
            this.k = k;
            this.v = v;
            this.next = next;
            this.hash = hash;
        }

    }

}
