import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
//個別株データ(201XXX_contract.txt)のマイクロ秒について確認するプログラム
public class kobetsu_diftime{

    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	/*String record1;//取引所コード
        	String security;//証券種別
        	String code1;//銘柄コード(a,b)
        	String code2;//銘柄コード(限月)
        	String code3;//銘柄コード(c,d)
        	String record2;//(レコード種別)
        	int day;//日付*/
        	//int count_code=0;
        	//int count_meigara = 0;

        	int length_konma = 0;//コンマデータの長さの確認
        	int count1 = 0;//for文
        	int count2 = 0;//for文
        	int count3 = 0;//for文
        	int count4 = 0;//for文


        	int count_if = 0;//for文

        	//int number1 = 0;
        	//int number2 = 0;
        	String a;
        	int i1 = 0;
        	int i2 = 0;
        	int i3 = 0;
        	int i4 = 0;
        	int i5 = 0;


        	int day = 0;

        	double hour = 0.0;
        	double minute = 0.0;
        	double second = 0.0;
        	double micro = 0.0;
        	double milli = 0.0;

        	double time_sum = 0.0;
        	double time_sum1 = 0.0;
        	double time_sum2 = 0.0;
        	double time_dif = 0.0;

        	int count_dif = 0;


        	int day_dummy = 0;




        	/*int count_a = 0;
        	int count_b = 0;
        	int count_c = 0;*/






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\_");

         	File file = new File("dif_" + filename[0] + "_"
         	+ filename[1] + //limit or market
         	//+ "cancel" + //cancel
         	"_" + filename[2] + "_" + filename[3]);//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));


            while ((line = brtxt.readLine()) != null) {


            	Index = line;

            	/*length_konma = Index.length();//個別株の場合　ここから

        		for(i1=0;i1<length_konma;i1++){
                    a = Index.substring(i1,i1+1);

                    if(count_if == 1 && !(a.equals(",")) && count1 == 0){
                    	i2 = i1+1;
                    	count1++;
                    }
                    if(count_if == 2 && count1 != 0 && count2 == 0){
                    	i3 = i1-2;
                    	count2++;
                    	String d0 = Index.substring(i2,i3);
                    	day = Integer.parseInt(d0);
                    }
                    if(count_if == 9 && !(a.equals(",")) && count3 == 0){
                    	i4 = i1+1;
                    	count3++;
                    }
                    if(count_if == 10 && count3 != 0 && count4 == 0){
                    	i5 = i1-2;
                    	count4++;
                    	String d1 = Index.substring(i4,i5-10);
                    	String d2 = Index.substring(i4+2,i5-8);
                    	String d3 = Index.substring(i4+4,i5-6);
                    	String d4 = Index.substring(i4+6,i5);
                    	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                    	hour = Double.parseDouble(d1);
                    	minute = Double.parseDouble(d2);
                    	second = Double.parseDouble(d3);
                    	micro = Double.parseDouble(d4);

                    	hour = hour*60*60;
                    	minute = minute*60;
                    	micro = micro/1000000;
                    	time_sum = hour + minute + second + micro;
                    }
                    if(a.equals(",")){
                        count_if++;
                    }
                }
        		count_if = 0;
        		count1 = 0;
        		count2 = 0;
        		count3 = 0;
        		count4 = 0;

        		i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;//個別株の場合　ここまで*/

            	length_konma = Index.length();

        		for(i1=0;i1<length_konma;i1++){//2016年取得ロイター　ここから
                    a = Index.substring(i1,i1+1);

                    if(count_if == 2 && !(a.equals(",")) && count1 == 0){
                    	i2 = i1;
                    	count1++;
                    }
                    if(count_if == 3 && count1 != 0 && count2 == 0){
                    	i3 = i1-1;
                    	count2++;
                    	String d0 = Index.substring(i2,i3);
                    	day = Integer.parseInt(d0);
                    }
                    if(count_if == 3 && !(a.equals(",")) && count3 == 0){
                    	i4 = i1;
                    	count3++;
                    }
                    if(count_if == 4 && count3 != 0 && count4 == 0){
                    	i5 = i1-1;
                    	count4++;
                    	String d1 = Index.substring(i4,i5-13);
                    	String d2 = Index.substring(i4+3,i5-10);
                    	String d3 = Index.substring(i4+6,i5-7);
                    	String d4 = Index.substring(i4+9,i5);//マイクロ秒
                    	//String d4 = Index.substring(i4+9,i5-3);//ミリ秒
                    	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                    	hour = Double.parseDouble(d1);
                    	minute = Double.parseDouble(d2);
                    	second = Double.parseDouble(d3);
                    	micro = Double.parseDouble(d4);//マイクロ秒
                    	//milli = Double.parseDouble(d4);//ミリ秒

                    	hour = hour*60*60;
                    	minute = minute*60;
                    	micro = micro/1000000;
                    	milli =milli/1000;
                    	time_sum = hour + minute + second + micro;//マイクロ秒
                    	//time_sum = hour + minute + second + milli;//ミリ秒

                    }
                    if(a.equals(",")){
                        count_if++;
                    }
                }
        		count_if = 0;
        		count1 = 0;
        		count2 = 0;
        		count3 = 0;
        		count4 = 0;

        		i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;//2016年取得ロイター　ここまで

            	//limit_order_final_trade(ここから）

            	/*String JNIc_split[] = line.split(",", 0);
            	day = Integer.parseInt(JNIc_split[0]);
            	hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
            	minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
            	second = Double.parseDouble(JNIc_split[1].substring(6));//秒
            	time_sum = hour*3600 + minute*60 + second;//時間を秒換算
            	int volume = Integer.parseInt(JNIc_split[2]);
            	//limit_order_final_trade(ここまで）*/

            	//System.out.println(volume);

                if(count_dif == 0){
                	time_sum1 = time_sum;
                	count_dif++;
                	day_dummy = day;
                }
                else if((day == 20061227 && 38700 <= time_sum) || //10:45以降
                		(day == 20081222 && time_sum <= 45000) || //12:30以降
                		(day == 20081010 && 32400 <= time_sum && time_sum <= 34200) ||
                		(day == 20081014 && 32400 <= time_sum && time_sum <= 34200) ||
                		(day == 20081016 && 32400 <= time_sum && time_sum <= 34200) ||
                		(day == 20110314 && 32400 <= time_sum && time_sum <= 33300) ||
                		(day == 20110315 && 39600 <= time_sum && time_sum <= 42300) ||
                		(day == 20110315 && 43200 <= time_sum && time_sum <= 44100) ||
                		(day == 20110315 && 45900 <= time_sum && time_sum <= 46800) ||
                		(day == 20130304 && 39600 <= time_sum && time_sum <= 51300) ||
                		(day == 20110523 && 52200 <= time_sum && time_sum <= 53100) ||
                		(day == 20140304 && 39600 <= time_sum && time_sum <= 41400)
                		){
                	System.out.println(line);

                }
                else if(day_dummy == day){
                	time_sum2 = time_sum;
                	time_dif = time_sum2 - time_sum1;
                	BigDecimal x1 = new BigDecimal(time_dif);
            		time_dif = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//マイクロ秒
            		//time_dif = x1.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();//ミリ秒

            		if(time_dif > 0){//時間差に0を含むか含まないか　これは非常に重要．volumeは指値注文のとき，キャンセルなのか否かを判別するもの
            			pw.println(day + " " + time_dif);
            		}
            		/*else if(time_dif == 0){
            			pw.println(day + " 0.0000001");//limit_orderの場合（時間差0のときの処理）．volumeは指値注文のとき，キャンセルなのか否かを判別するもの
            		}*/
                	time_sum1 = time_sum;
                }
                else if(day_dummy != day){
                	time_sum1 = time_sum;
                	day_dummy = day;
                }
            }





            brtxt.close();
            fr.close();
            pw.close();

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}


