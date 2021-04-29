import com.company.Queue;

public class PriorityQueue<E> {
    private final Queue<E>[] buffers;

    @SuppressWarnings("unchecked")
    public PriorityQueue(int countBuffers) {
        if (countBuffers < 1)
            throw new IllegalArgumentException();
        buffers = (Queue<E>[]) new Queue[countBuffers];
        for (int i = 0; i < countBuffers; i++)
            buffers[i] = new Queue<>();
    }
    public PriorityQueue() {
        this(1);
    }

    public int buffersSize() {
        return buffers.length;
    }
    public int size() {
        int s = 0;
        for (Queue<E> queue : buffers)
            s += queue.size();
        return s;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(E e) {
        return offer(e);
    }
    public boolean contains(Object o) {
        for (Queue<E> queue : buffers)
            if (queue.contains(o))
                return true;
        return false;
    }
    public boolean remove(Object o) {
        for (Queue<E> queue : buffers)
            if (queue.remove(o))
                return true;
        return false;
    }
    public void clear() {
        for (Queue<E> queue : buffers)
            queue.clear();
    }

    private Queue<E> maxPriority() {
        int i = 0;
        while (i < buffers.length && !buffers[i].isEmpty())
            i++;
        return buffers[i];
    }
    private void checkPriority(int priority) {
        if (priority < 0 || priority >= buffers.length)
            throw new IllegalArgumentException("Illegal priority: Buffers length = " + buffers.length);
    }

    public boolean offer(E e, int priority) {
        checkPriority(priority);
        return buffers[priority].offer(e);
    }
    public boolean offer(E e) {
        return buffers[0].offer(e);
    }

    public E remove() {
        return maxPriority().remove();
    }
    public E poll() {
        return maxPriority().poll();
    }
    public E element() {
        return maxPriority().element();
    }
    public E peek() {
        return maxPriority().peek();
    }
}