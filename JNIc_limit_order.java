import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//新しいTick Dataでの指値注文の注文量と時間を出力するプログラム　
//約定と約定の間にある指値を数えるプログラム(要するに指値注文を数えるプログラム）
//板に移動した直後の板の厚みも出力可能
public class JNIc_limit_order{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	int number1 = 0;//bidとaskの最良気配の価格決定で必要なもの

        	//文字の抽出のプログラムで必要（ここから）
        	String a;
            int count = 0;
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            int count6 = 0;
            int count7 = 0;
            int count8 = 0;
            int count9 = 0;
            int count10 = 0;
            int count11 = 0;
            int count12 = 0;
            int count13 = 0;
            //文字の抽出のプログラムで必要（ここまで）
            int count_trade_before = 0;//指値注文の直前に約定があったかどうか（同時刻に）　同じ時刻に指値注文が2個来ているもとがあるため（例：同時刻に成行注文・指値注文・指値注文）
            int count_hiruma1 = 0;//昼休みのfor文で必要な関数
            String day = null;//日付
            String transaction = null;//Quote or Trade
            String time = null;//時間
            double time1 = 0.0;//時
            double time2 = 0.0;//分
            double time3 = 0.0;//秒
            double time_total1 = 0.0;//時間を秒で表示(time1,time2,time3を使いながら)
            String day1 = null;//日付
            int day_number = 0;//年月日
            String bid_volume;//最良買気配の累積枚数
            int bid_volume1 = 0;//最良買気配の累積枚数
            int bid_volume2 = 0;//1つ前の最良買気配の累積枚数
            int bid_volume_dif = 0;//前の買いの指値注文からの増減
            String ask_volume = null;//最良売気配の累積枚数
            int ask_volume1 = 0;//最良売気配の累積枚数
            int ask_volume2 = 0;//1つ前の最良売気配の累積枚数
            int ask_volume_dif = 0;//前の売りの指値注文からの増減
            String trade_price;//約定価格
            int trade_price1 = 0;//約定価格
            String trade_volume;//出来高
            int trade_volume1 = 0;//出来高
            String bid;//最良買気配の値段
            int bid1[] = new int[800000];//最良買気配の値段
            String ask;//最良売気配の値段
            int ask1[] = new int[800000];//最良売気配の値段
            int bid_price_before = 0;//１つ前の最良買気配の値段
            int ask_price_before = 0;//１つ前の最良売気配の値段
            int ita_change = 0;//直前に板の移動があったかどうか示す変数（１→買板が消滅、２→売板が消滅、０→なし）
            int bid_ask_initialization = 0;//板の移動があった場合の初期化変数（１→買板が消滅、２→売板が消滅、０→なし）
            int bid_price_same = 0;//前の買板と今の買板が同じかどうか(同じのとき→1、違うとき→0)
            int ask_price_same = 0;//前の売板と今の売板が同じかどうか(同じのとき→1、違うとき→0)

            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;

            double trade_time = 0.0;//約定の時間（計算結果）


        	//FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎/" + txtFileName);//Macの場合
            //FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎(限月調整3,6,9,12)/結合データ/" + txtFileName);//Macの場合
        	FileReader fr = new FileReader(txtFileName);//Windowsの場合
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\_");

            //指値注文の出力プログラム
            File file = new File(filename[1].substring(0,6) + "_limit_order.csv");//Windows
         	//File file = new File("/Volumes/HASHIMOTO3/data/2016/指値データ/ロイター通信社指値注文/月毎(900-1510)/JNIc_" + filename[1].substring(0,6) + "_limit_order.csv");//Mac
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

         	//板が移動した直後の板の厚さを出力するプログラム
         	//File file_depth = new File(filename[1].substring(0,6) + "_frist_depth.csv");
          	//PrintWriter pw_depth = new PrintWriter(new BufferedWriter(new FileWriter(file_depth)));

         	//約定の取引を出力するプログラム
         	//File file = new File(filename[1].substring(0,6) + "_market_order.csv");
          	//PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));


            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	i2 = Index.length();

            	if(!(Index.substring(0,1).equals("#"))){

                	day = line.substring(12,20);//年月日
                	day_number = Integer.parseInt(day);//年月日
                	time = line.substring(21,36);//時間
                	transaction = line.substring(37,42);//Quote or Trade
            		String c1 = Index.substring(21,23);//時
            		String c2 = Index.substring(24,26);//分
            		String c3 = Index.substring(27,36);//秒
            		time1 = Double.parseDouble(c1);//時
            		time2 = Double.parseDouble(c2);//分
            		time3 = Double.parseDouble(c3);//秒
            		time_total1 = time1*3600 + time2*60 + time3;//時間を秒で表示

                	if(!(day.equals(day1))){//日付が変わった際に初期化する箇所
                		count_hiruma1 = 0;//初期化
                		number1 = 0;//初期化
                		bid_volume1 = 0;//初期化
                		ask_volume1 = 0;//初期化
                    	bid1[0]=0;//初期化
                    	ask1[0]=0;//初期化
                    	bid1[1]=0;//初期化
                    	ask1[1]=0;//初期化
                    	count13 = 0;//初期化
                	}

                	//ここを変えた！！！！
                	if(bid1[1] == ask1[1] && time_total1 > 39600.0 && count_hiruma1 == 0 && day_number < 20110214){//2011/2/14までは昼休みがあるのでそこに関して調整する箇所
                		number1 = 0;//初期化
                		bid_volume1 = 0;//初期化
                		ask_volume1 = 0;//初期化
                    	bid1[0]=0;//初期化
                    	ask1[0]=0;//初期化
                    	bid1[1]=0;//初期化
                    	ask1[1]=0;//初期化
                		count_hiruma1++;//このfor文を回避するためのもの
                		count13 = 0;//初期化
                	}
                	//ここを変えた！！！！

                	String JNIc_split[] = line.split(",", 0);

                	if(JNIc_split[4].equals("Trade")){
                		trade_price = JNIc_split[5];//Tradeの約定価格
                    	trade_price1 =Integer.parseInt(trade_price);
                    	trade_volume = JNIc_split[6];//Tradeの出来高
                    	trade_volume1 =Integer.parseInt(trade_volume);
                	}
                	else if(JNIc_split[4].equals("Quote")){
                		bid = JNIc_split[8];//最良買気配の値段
                		if(!(bid.equals(""))){//JNIcにはこのデータがnullのものがある。
                			if(bid1[number1] == Integer.parseInt(JNIc_split[8])){
                				bid_price_same = 1;
                			}
                			else{
                				bid_price_same = 0;
                			}
                        	bid1[number1] = Integer.parseInt(bid);
                		}
                    	bid_volume = JNIc_split[9];//最良売気配の累積枚数
                    	bid_volume1 =Integer.parseInt(bid_volume);
                    	ask = JNIc_split[10];//最良売気配の値段
                    	if(!(ask.equals(""))){//JNIcにはこのデータがnullのものがある。
                    		if(ask1[number1] == Integer.parseInt(JNIc_split[10])){
                				ask_price_same = 1;
                			}
                			else{
                				ask_price_same = 0;
                			}
                    		ask1[number1] = Integer.parseInt(ask);
                    	}
                        ask_volume = JNIc_split[11];//最良売気配の累積枚数
                    	ask_volume1 =Integer.parseInt(ask_volume);
                	}

                    if(bid1[1]>ask1[1] && bid1[1] != 0 && ask1[1] != 0){//意味の分からないことが起きていないか確認(買値＞売値)

                    	System.out.println(day + " " + time + " " + bid1[1] + " " + ask1[1]);

                    }
                    if(transaction.equals("Trade")){
                    	trade_time = time_total1;
                    	count_trade_before = 0;

                    	if(bid1[1] == trade_price1){
                    		bid_volume2 = bid_volume2 - trade_volume1;
                    		if(bid_volume2  <= 0){//板の移動
                    			ita_change = 1;
                    		}
                    	}

                    	if(ask1[1] == trade_price1){
                    		ask_volume2 = ask_volume2 - trade_volume1;
                    		if(ask_volume2 <= 0){//板の移動
                    			ita_change = 2;
                    		}
                    	}

                    	if(count13 != 0){
                    		if(bid1[1] != trade_price1 && ask1[1] != trade_price1 && bid1[1] != 0 && ask1[1] != 0){
                        		//System.out.println(Index + "+++" + bid1[1] + "," + ask1[1]);
                        	}

                    		/*if(trade_price1 == bid1[1]){//約定プログラム（ここから）
                    			pw.println(day + "," + time + "," + trade_volume1 + "," + trade_price1 + ",ask,,,,,");
                    		}
                    		else if(trade_price1 == ask1[1]){
                    			pw.println(day + "," + time + "," + trade_volume1 + "," + trade_price1 + ",bid,,,,,");
                    		}
                    		else{
                    			pw.println(day + "," + time + "," + trade_volume1 + "," + trade_price1 + ",error2,,,,,");
                    		}*///約定プログラム（ここまで）
                    	}
                    	else if(count13 == 0){
                    		//pw.println(day + "," + time + "," + trade_volume1 + "," + trade_price1 + ",error1,,,,,");//約定プログラム
                    	}
                    	else{
                    		System.out.println("error");
                    	}


                    }
                    if((bid1[0] != ask1[0] && ask1[0] != 0 && bid1[0] != 0 && count13 == 0 && transaction.equals("Quote")) ||
                    		(bid1[0] != ask1[0] && ask1[0] != 0 && bid1[0] != 0 && (transaction.equals("Quote") && (bid_ask_initialization != 0 || ask_price_same == 0 || bid_price_same == 0)))){//寄り付き、板の移動(bid_ask_initialization)などが終了したときの初期値の設定

                    	if(count13 == 0){
                    		bid1[1] = bid1[0];
                        	ask1[1] = ask1[0];
                    	}
                    	bid_volume2 = bid_volume1;
                    	ask_volume2 = ask_volume1;


                    	/*if(bid_ask_initialization == 1 && bid1[1] != 0 && ask1[1] != 0){//板が移動した直後の板の厚さ(価格差の制約をつけると厳しくなる模様）
                    		if(bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] == 10){//板が追随して下落（価格差10円）
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] != 10){//板が追随して下落(価格差は20円以上）
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down price dif not 10," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 1 && ask_price_same == 0){//売板のみ下落
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down only ask," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 0 && ask_price_same == 1){//買板のみ下落
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down only bid," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 1 && ask_price_same == 1){//板が追随して下落したと思いきや、価格は変わらないもの
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down price same," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else{//その他
                    			System.out.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down else," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    	}
                    	else if(bid_ask_initialization == 2 && bid1[1] != 0 && ask1[1] != 0){//板が移動した直後の板の厚さ
                    		if( bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] == 10){
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] != 10){
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up price dif not 10," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 1 && ask_price_same == 0){
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up only ask," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 0 && ask_price_same == 1){
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up only bid," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_same == 1 && ask_price_same == 1){
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up price same," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else{
                    			System.out.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up else," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    	}
                    	else if(bid_ask_initialization == 0 && count13 != 0 && bid1[1] != 0 && ask1[1] != 0){//注文の取り消しやなにかしらの理由による板の移動
                    		if(bid_price_before == bid1[1] && ask_price_before <= ask1[1] && bid_price_same == 1 && ask_price_same == 0){//売板のみの上昇
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up only ask not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before == bid1[1] && ask_price_before >= ask1[1] && bid_price_same == 1 && ask_price_same == 0){//売板のみの下落
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down only ask not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before <= bid1[1] && ask_price_before == ask1[1] && bid_price_same == 0 && ask_price_same == 1){//買板のみの上昇
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up only bid not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before >= bid1[1] && ask_price_before == ask1[1] && bid_price_same == 0 && ask_price_same == 1){//買板のみの下落
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down only bid not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before <= bid1[1] && ask_price_before <= ask1[1] && bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] == 10){//板の上昇（取り消しが移動の一因となる移動）
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before <= bid1[1] && ask_price_before <= ask1[1] && bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] != 10){//板の上昇（取り消しが移動の一因となる移動）。ただし価格差が20円以上
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",up not Trade not 10," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before >= bid1[1] && ask_price_before >= ask1[1] && bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] == 10){//板の下落（取り消しが移動の一因となる移動）
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down not Trade," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else if(bid_price_before >= bid1[1] && ask_price_before >= ask1[1] && bid_price_same == 0 && ask_price_same == 0 && ask1[1] - bid1[1] != 10){//板の下落（取り消しが移動の一因となる移動）。ただし価格差が20円以上
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",down not Trade not 10," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    		else{
                    			pw_depth.println(JNIc_split[2]+ "," + JNIc_split[3] + ",move," + bid1[1] + "," + bid_volume2 + "," + ask1[1] + "," + ask_volume2);
                    		}
                    	}*/


                    	number1 = 1;
                    	day1 = day;



                    	count13 = 1;


                    	//System.out.println(bid_ask_initialization);
                    	bid_ask_initialization = 0;
                    	//System.out.println(Index);

                    }
                    if(count13 != 0){//共通

                    		if(bid_volume1 != 0 && ask_volume1 != 0 && !(transaction.equals("Trade"))){
                    			//System.out.println(time_total1);
                        		if(trade_time == time_total1 && trade_price1 == bid1[1] && count_trade_before == 0){//約定と同時に起きた指値注文
                        			if(trade_price1 == bid1[1]){
                        				if(bid_volume2 < bid_volume1){
                            				bid_volume_dif = bid_volume1 - bid_volume2;
                            				//System.out.println(Index + " " + bid_volume_dif);
                            			}
                            			else if(bid_volume2 == bid_volume1){//注文の取り消しとか
                            				bid_volume_dif = 0;
                            			}
                            			else if(bid_volume2 > bid_volume1){
                            				bid_volume_dif = bid_volume1 - bid_volume2;
                            			}
                            			else{
                            				System.out.println(line);
                            			}

                            			count_trade_before++;
                            			ita_change = 0;
                        			}
                        		}
                        		else if(bid_price_same == 1){//指値注文
                        			bid_volume_dif = bid_volume1 - bid_volume2;
                        			ita_change = 0;
                        		}
                        		else if(bid_price_same == 0){
                        			bid_volume_dif = 0;
                        			ita_change = 0;
                        		}
                        		else{
                        			ita_change = 0;
                        		}
                        		bid_volume2 = bid_volume1;//1つ前の注文にしている。
                        		bid_price_before = bid1[1];//1つ前の最良買気配の値段の代入

                        		if(trade_time == time_total1 && trade_price1 == ask1[1] && count_trade_before == 0){//約定と同時に起きた指値注文
                        			if(trade_price1 == ask1[1]){
                        				if(ask_volume2 < ask_volume1){
                            				ask_volume_dif = ask_volume1 - ask_volume2;

                            				//System.out.println(Index + " " + ask_volume_dif);
                            				/*if(day.equals("20060105")){
                            					System.out.println(trade_volume1 + " " + ask_volume2 + " " + ask_volume1);
                            					System.out.println(Index);
                            				}*/

                            			}
                        				else if(ask_volume2 == ask_volume1){//ただの約定
                        					//System.out.println(line);
                        					ask_volume_dif = 0;
                        				}
                        				else if(ask_volume2 > ask_volume1){//注文の取り消し
                        					//System.out.println(line);
                        					ask_volume_dif = ask_volume1 - ask_volume2;
                            			}
                        				else{

                        				}
                            			count_trade_before++;
                            			ita_change = 0;
                        			}
                        		}
                        		else if(ask_price_same == 1){//指値注文
                        			ask_volume_dif = ask_volume1 - ask_volume2;
                        			ita_change = 0;
                        		}
                        		else if(ask_price_same == 0){
                        			bid_volume_dif = 0;
                        			ita_change = 0;
                        		}
                        		else{
                        			ita_change = 0;
                        		}
                        		ask_volume2 = ask_volume1;//1つ前の注文にしている。
                        		ask_price_before = ask1[1];//1つ前の最良売気配の値段の代入



                        		if(ask_volume_dif != 0){
                        			pw.println(day + "," + time + "," + ask_volume_dif + "," + ask1[1] + ",ask,,,,,");//指値売注文の書き込み

                        		}
                        		if(bid_volume_dif != 0){
                        			pw.println(day + "," + time + "," + bid_volume_dif + "," + bid1[1] + ",bid,,,,,");//指値買注文の書き込み

                        		}



                    		}
                    		if(ita_change != 0){
                    			bid_ask_initialization = ita_change;
                    			ita_change = 0;
                    			//System.out.println(Index);
                    		}


                    }


                    count1=0;
                    count2=0;
                    count3=0;
                    count4=0;
                    count5=0;
                    count6=0;
                    count7=0;
                    count8=0;
                    count9=0;
                    count10=0;
                    count11=0;
                    count12=0;
                    count=0;
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    i6 = 0;
                    i7 = 0;
                    i8 = 0;
                    i9 = 0;
                    i10 = 0;

            	}


            }


            brtxt.close();
            fr.close();
            pw.close();
            //pw_depth.close();


        }

        br.close();
    }
}

