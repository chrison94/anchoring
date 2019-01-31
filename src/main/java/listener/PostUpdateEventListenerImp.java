package listener;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import functions.CreateLists;
import functions.Hashing;
import functions.WavesDataTransactions;
import functions.WavesDataTransactionsTimestamp;
import threadpool.Threadpool;

public class PostUpdateEventListenerImp implements PostUpdateEventListener {
   
   private static final long serialVersionUID = 1L;
   CreateLists cl = new CreateLists();
   Threadpool tr = new Threadpool();  
   
   public void onPostUpdate(PostUpdateEvent sa) {
	   String methodName;
	   String hashResult = null;
	   String hashResultTimestamp = null;
	   Hashing hs = new Hashing();
	   System.out.println(sa);
	   System.out.println("------------------------ UPDATE ------------------------");
   		
	   try {  
		Timestamp time = new Timestamp(System.currentTimeMillis());
		long getTimestamp = time.getTime();
   		Thread.sleep(1);
     	  Object cla = sa.getEntity();
     	  System.out.println(cla);
     	  Class<?> c = cla.getClass();
     	       	  
     	  Object entryId = null;
     	  List<Object> data = new ArrayList<Object>();
     	  List<Method> allMethods = new ArrayList<Method>(Arrays.asList((c.getDeclaredMethods())));
     	  
     	  for(Method m : allMethods) {
     		  methodName = m.getName();
     		  if(!data.contains(c.getSimpleName())) {
     			 data.add(c.getSimpleName());  
     		  }   
     		 if(methodName.toLowerCase().contains("getid")) {
     			entryId = m.invoke(cla);     			
     		 }
     		  if(methodName.toLowerCase().contains("get")) {     			 
        		  Object result = m.invoke(cla);  
     			  data.add(result);
       		  }
     	  }		  
     	  System.out.println(data + "abcef");
     	  hashResult = hs.DatabaseEntryHash(data);

     	  if(!cl.getHashList().contains(hashResult)) {
     		  cl.addHashList(hashResult);
     		  cl.addTimestamp(getTimestamp);
     		  cl.addTableName(c.getSimpleName());
     		  cl.addDbEntry(entryId.toString());    		  
     		  hashResultTimestamp = hs.DatabaseEntryHashTimestamp(hashResult, getTimestamp);
     		 cl.addHashTimestamp(hashResultTimestamp);
     	  } else {
     		  int indexOfDouble = cl.getHashList().indexOf(hashResult);
     		      		  
     		  /* remove double entry */
     		  cl.removeDouble(indexOfDouble);
     		  
     		  /* add new entry */
     		  cl.addHashList(hashResult);
     		  cl.addTimestamp(getTimestamp);
     		  cl.addTableName(c.getSimpleName());
     		  cl.addDbEntry(entryId.toString());
     		  hashResultTimestamp = hs.DatabaseEntryHashTimestamp(hashResult, getTimestamp);
        	  cl.addHashTimestamp(hashResultTimestamp); 		  
     	  }
     	  
     	  if(cl.getHashListSize() == 100) {
     		 tr.threadpoolHandle(cl.getHashList(), cl.getTimestampList());     		 
     		 tr.threadpoolHandleTimestamp(cl.getHashListTimestamp(), cl.getTimestampList(), cl.getTableNameList(), cl.getEntryIDList());
     		 cl.clearLists();
     	  }
     	  if(c.getSimpleName().contains("backup")) {
     		 //tr.threadpoolHandle(cl.getHashList(), cl.getTimestampList());	     		 
     		// tr.threadpoolHandleTimestamp(cl.getHashListTimestamp(), cl.getTimestampList(), cl.getTableNameList(), cl.getEntryIDList());
     	  }

	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

@Override
public boolean requiresPostCommitHanding(EntityPersister persister) {
	// TODO Auto-generated method stub
	return false;
}
}