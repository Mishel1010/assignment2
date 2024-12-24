package bgu.spl.mics;
import java.util.AbstractMap;
import java.util.concurrent.*;
import java.util.Map;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//fields
	private ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<MicroService>> events;
    private ConcurrentHashMap<Class<? extends Broadcast>, ConcurrentLinkedQueue<MicroService>> broadcasts;
    private ConcurrentHashMap<MicroService, ConcurrentLinkedQueue<Message>> microservices;
	private  ConcurrentHashMap <MicroService, ConcurrentLinkedQueue<Class<? extends Message>>> subscriptions;
	private static volatile MessageBusImpl instance;

	private MessageBusImpl() {
		events = new ConcurrentHashMap<>();
		broadcasts = new ConcurrentHashMap<>();
		microservices = new ConcurrentHashMap<>();
		subscriptions = new ConcurrentHashMap<>();
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		events.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).offer(m);
		subscriptions.computeIfAbsent(m, key -> new ConcurrentLinkedQueue<>()).offer(type);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		broadcasts.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).offer(m);
		subscriptions.computeIfAbsent(m, key -> new ConcurrentLinkedQueue<>()).offer(type);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		e.resolveFuture(result);
    }

	@Override
	public void sendBroadcast(Broadcast b) { //sends the broadcast to all of the subscribers
		ConcurrentLinkedQueue<MicroService> queue = broadcasts.get(b.getClass());
		if (queue != null) 
		{
			for (MicroService mike: queue)
			{
				microservices.get(mike).offer(b);
				synchronized (microservices.get(mike))
				{
					microservices.get(mike).notifyAll();
				}
			}
		}
	}
	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		ConcurrentLinkedQueue<MicroService> q = events.get(e.getClass());
		if (q == null || q.peek() == null) 
		{
			return null;
		}
		MicroService a;
		synchronized(q)   
		{
			a = q.poll();  //transferring the first microservice in the queue to be the last
		    q.offer(a);
		}
		ConcurrentLinkedQueue<Message> aQueue = microservices.get(a);
        synchronized (aQueue)
		{
            aQueue.offer(e);     //adding the event to the microservice's queue
            aQueue.notifyAll(); // Notify the thread waiting on this queue
        }
    
		return e.getFuture();
	}   

	@Override
	public void register(MicroService m) {
		microservices.put(m, new ConcurrentLinkedQueue());
	}

	@Override
	public synchronized void  unregister(MicroService m) {
		for (Class<? extends Message> note : subscriptions.get(m))
		{
			if (!(events.get(note).remove(m)))
			{
				broadcasts.get(note).remove(m);
	    	}	
		}	
		subscriptions.remove(m);
		microservices.remove(m);
	}

	@Override
	public synchronized Message awaitMessage(MicroService m)  {
		while (microservices.get(m).isEmpty()) { //has no messages in it's queue
			try
			{
				m.wait();
			}
			catch(InterruptedException ignored){}		
		}
		return microservices.get(m).poll();
	}

	public static MessageBusImpl getInstance(){
		if (instance == null)
		{
			synchronized (MessageBusImpl.class)
			{
				if (instance == null)
				{
					instance = new MessageBusImpl();
				}
			}
		}
		return instance; 
	}
}