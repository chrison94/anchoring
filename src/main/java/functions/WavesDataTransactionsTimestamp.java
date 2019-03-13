package functions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.wavesplatform.wavesj.Account;
import com.wavesplatform.wavesj.DataEntry;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.PrivateKeyAccount;

public class WavesDataTransactionsTimestamp implements Runnable {
	
	private List<Object> hashList;
	private List<Object> timestampList;
	private List<Object> tableName;
	private List<Object> entryID;
	
	public WavesDataTransactionsTimestamp(final List<Object> hashList, final List<Object> timestampList, final List<Object> tableName, final List<Object> entryID) {
		this.hashList = hashList;
		System.out.println("wd"+hashList.get(1));
		this.timestampList = timestampList;
		this.tableName = tableName;
		this.entryID = entryID;
	}
	
	@Override
	public void run() {
		sendHashTimestamp();
	}

	private void sendHashTimestamp()  {
		String seed = "spike mad lonely paper fiber give thrive bind blush wide test nest surge vault misery";
	    PrivateKeyAccount account = PrivateKeyAccount.fromSeed(seed, 0, Account.TESTNET);
		String address = account.getAddress();
	//	System.out.println(address);
		Node node = new Node();
	    long fee = 1300000;
	    int i = 0;
	    List<DataEntry<?>> data = new LinkedList<DataEntry<?>>();
	    
	    System.out.println("SIZE " + this.hashList.size());
	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    for(Object hash : this.hashList) {
            String timestamp = this.timestampList.get(i).toString();           
            String tableNameVal = this.tableName.get(i).toString();
            String entryIdVal = this.entryID.get(i).toString();           
            try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
	    	data.add(new DataEntry.StringEntry(hash.toString(), timestamp + "/*/~/*/" + tableNameVal + "/*/~/*/" + entryIdVal));   
	    	i++;
	    }
	    
	    try {
		node.data(account, data, fee);
		   // System.out.println("The DataTransaction was executed for the address: " + address);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}