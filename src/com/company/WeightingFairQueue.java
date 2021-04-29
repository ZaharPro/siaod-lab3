package com.company;

import java.util.ArrayList;
import java.util.List;

public class WeightingFairQueue<E> {
    private static class WeightNode<E> {
        int weight;
        Queue<E> queue;

        public WeightNode(int weight, Queue<E> queue) {
            if (weight < 1)
                throw new IllegalArgumentException();
            if (queue == null)
                throw new NullPointerException();
            this.weight = weight;
            this.queue = queue;
        }

        @Override
        public String toString() {
            return "{weight=" + weight + "=" + queue + '}';
        }
    }

    private final Queue<E> outputQueue  = new Queue<>();
    private final Queue<E> priorityQueue = new Queue<>();
    private final List<WeightNode<E>> weightQueues = new ArrayList<>();
    private int index = 0;

    public WeightingFairQueue() {
    }
    public WeightingFairQueue(int... weights) {
        for (int currentWeight : weights)
            createNewWQ(currentWeight);
    }

    public int size() {
        int s = outputQueue.size() + priorityQueue.size();
        for (WeightNode<E> weightNode : weightQueues)
            s += weightNode.queue.size();
        return s;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(E e) {
        return offer(e);
    }

    public boolean contains(Object o) {
        if (outputQueue.contains(o))
            return true;
        if (priorityQueue.contains(o))
            return true;
        for (WeightNode<E> weightNode : weightQueues)
            if (weightNode.queue.contains(o))
                return true;
        return false;
    }
    public boolean remove(Object o) {
        if (outputQueue.remove(o))
            return true;
        if (priorityQueue.remove(o))
            return true;
        for (WeightNode<E> weightNode : weightQueues)
            if (weightNode.queue.remove(o))
                return true;
        return false;
    }
    public void clear() {
        outputQueue.clear();
        priorityQueue.clear();
        for (WeightNode<E> weightNode : weightQueues)
            weightNode.queue.clear();
        index = 0;
    }

    private void checkOutputQueue() {
        if (outputQueue.isEmpty()) {
            if (priorityQueue.isEmpty()) {
                searchElementsInWeightQueues();
            } else {
                outputQueue.offer(priorityQueue.remove());
            }
        }
    }
    private void searchElementsInWeightQueues() {
        int countAdded = 0;
        for (int i = 0; i < weightQueues.size() && countAdded == 0; i++) {
            WeightNode<E> node = weightQueues.get(index);
            while (countAdded < node.weight && !node.queue.isEmpty()) {
                outputQueue.offer(node.queue.remove());
                countAdded++;
            }
            nextIndex();
        }
    }
    private void nextIndex() {
        if (++index >= weightQueues.size())
            index = 0;
    }

    public boolean offer(E e) {
        return priorityQueue.offer(e);
    }
    public E remove() {
        checkOutputQueue();
        return outputQueue.remove();
    }
    public E poll() {
        checkOutputQueue();
        return outputQueue.poll();
    }
    public E element() {
        checkOutputQueue();
        return outputQueue.element();
    }
    public E peek() {
        checkOutputQueue();
        return outputQueue.peek();
    }

    public int sizeWQ() {
        return weightQueues.size();
    }
    public void createNewWQ(int weight) {
        createNewWQ(weight, new Queue<>());
    }
    public void createNewWQ(int weight, Queue<E> queue) {
        weightQueues.add(new WeightNode<>(weight, queue));
    }

    public int indexOfWQ(Object o) {
        if (o != null)
            for (int i = 0; i < weightQueues.size(); i++)
                if (o.equals(weightQueues.get(i)))
                    return i;
        return -1;
    }
    public boolean containsWQ(Object o) {
        return indexOfWQ(o) > -1;
    }

    public boolean removeWQ(Object o) {
        int i = indexOfWQ(o);
        if (i > -1) {
            removeWQ(i);
            return true;
        }
        return false;
    }
    public void removeWQ(int index) {
        weightQueues.remove(index);
        if (this.index > index)
            this.index--;
    }
    public boolean offerInWQ(E e, int index) {
        return weightQueues.get(index).queue.offer(e);
    }

    @Override
    public String toString() {
        return "WeightingFairQueue{outputQueue=" + outputQueue +
                "\nmaxPriorityQueue=" + priorityQueue +
                "\nweightQueues=" + weightQueues + '}';
    }
}