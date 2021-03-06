import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class depth_count{
	//JNIc_limit_order.javaから作成したdepthファイルから、板の移動回数や最初の板の枚数を計算するプログラム

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist_depth.txt"));//読み取りたいファイル名の記入
		String txtFileName;


		double hour = 0;//時間
		double minute = 0;//分
		double second = 0;//秒
		double time_total = 0;//累積時間（秒）

		int up_count = 0;//板が上昇した回数（2011/2/14以降）
		int up_count_morning = 0;//板が上昇した回数（前場）
		int up_count_afternoon = 0;//板が上昇した回数（後場）
		int up_bid_depth_count = 0;//板が上昇したときの累積買板枚数(2011/2/14以降)
		int up_bid_depth_count_morning = 0;//板が上昇したときの累積買板枚数(前場）
		int up_bid_depth_count_afternoon = 0;//板が上昇したときの累積買板枚数（後場）
		int up_ask_depth_count = 0;//板が上昇したときの累積売板枚数(2011/2/14以降)
		int up_ask_depth_count_morning = 0;//板が上昇したときの累積売板枚数(前場）
		int up_ask_depth_count_afternoon = 0;//板が上昇したときの累積売板枚数（後場）

		int down_count = 0;//板が下落した回数（2011/2/14以降）
		int down_count_morning = 0;//板が下落した回数（前場）
		int down_count_afternoon = 0;//板が下落した回数（後場）
		int down_bid_depth_count = 0;//板が下落したときの累積買板枚数(2011/2/14以降)
		int down_bid_depth_count_morning = 0;//板が下落したときの累積買板枚数(前場）
		int down_bid_depth_count_afternoon = 0;//板が下落したときの累積買板枚数（後場）
		int down_ask_depth_count = 0;//板が下落したときの累積売板枚数(2011/2/14以降)
		int down_ask_depth_count_morning = 0;//板が下落したときの累積売板枚数(前場）
		int down_ask_depth_count_afternoon = 0;//板が下落したときの累積売板枚数（後場）

		int up_count_dif_not_10_morning = 0;//価格差20円以上で買板・売板が上昇した数（前場）
		int up_count_dif_not_10_afternoon = 0;//価格差20円以上で買板・売板が上昇した数（前場）
		int up_count_dif_not_10 = 0;//価格差20円以上で買板・売板が上昇した数（(2011/2/14以降）

		int down_count_dif_not_10_morning = 0;//価格差20円以上で買板・売板が下落した数（前場）
		int down_count_dif_not_10_afternoon = 0;//価格差20円以上で買板・売板が下落した数（前場）
		int down_count_dif_not_10 = 0;//価格差20円以上で買板・売板が下落した数（(2011/2/14以降）

		int up_count_only_bid_morning = 0;//買板のみ上昇した数（前場）
		int up_count_only_bid_afternoon = 0;//買板のみ上昇した数（後場）
		int up_count_only_bid = 0;//買板のみ上昇した数（(2011/2/14以降）
		int up_count_only_ask_morning = 0;//売板のみ上昇昇した数（前場）
		int up_count_only_ask_afternoon = 0;//売板のみ上昇した数（後場）
		int up_count_only_ask = 0;//売板のみ上昇した数（(2011/2/14以降）

		int down_count_only_bid_morning = 0;//買板のみ下落した数（前場）
		int down_count_only_bid_afternoon = 0;//買板のみ下落した数（後場）
		int down_count_only_bid = 0;//買板のみ下落した数（(2011/2/14以降）
		int down_count_only_ask_morning = 0;//売板のみ下落昇した数（前場）
		int down_count_only_ask_afternoon = 0;//売板のみ下落した数（後場）
		int down_count_only_ask = 0;//売板のみ下落した数（(2011/2/14以降）

		int up_count_ask_up_bid_up_morning = 0;//売板のみが上昇した後に買板のみが上昇する回数（前場）
		int up_count_ask_up_bid_up_afternoon = 0;//売板のみが上昇した後に買板のみが上昇する回数（後場）
		int up_count_ask_up_bid_up = 0;//売板のみが上昇した後に買板のみが上昇する回数（2011/2/14以降）

		int up_bid_depth_ask_up_bid_up_morning = 0;//売板のみが上昇した後に買板のみが上昇したときの、買板の枚数（前場）
		int up_bid_depth_ask_up_bid_up_afternoon = 0;//売板のみが上昇した後に買板のみが上昇したときの、買板の枚数（後場）
		int up_bid_depth_ask_up_bid_up = 0;////売板のみが上昇した後に買板のみが上昇したときの、買板の枚数（2011/2/14以降）
		int up_ask_depth_ask_up_bid_up_morning = 0;//売板のみが上昇した後に買板のみが上昇したときの、売板の枚数（前場）
		int up_ask_depth_ask_up_bid_up_afternoon = 0;//売板のみが上昇した後に買板のみが上昇したときの、売板の枚数（後場）
		int up_ask_depth_ask_up_bid_up = 0;//売板のみが上昇した後に買板のみが上昇したときの、売板の枚数（2011/2/14以降）


		int down_count_ask_down_bid_down_morning = 0;//売板のみが下落した後に買板のみが下落する回数（前場）
		int down_count_ask_down_bid_down_afternoon = 0;//売板のみが下落した後に買板のみが下落する回数（後場）
		int down_count_ask_down_bid_down = 0;//売板のみが下落した後に買板のみが下落する回数（2011/2/14以降）

		int down_bid_depth_ask_down_bid_down_morning = 0;//売板のみが下落した後に買板のみが下落したときの、買板の枚数（前場）
		int down_bid_depth_ask_down_bid_down_afternoon = 0;//売板のみが下落した後に買板のみが下落したときの、買板の枚数（後場）
		int down_bid_depth_ask_down_bid_down = 0;////売板のみが下落した後に買板のみが下落したときの、買板の枚数（2011/2/14以降）
		int down_ask_depth_ask_down_bid_down_morning = 0;//売板のみが下落した後に買板のみが下落したときの、売板の枚数（前場）
		int down_ask_depth_ask_down_bid_down_afternoon = 0;//売板のみが下落した後に買板のみが下落したときの、売板の枚数（後場）
		int down_ask_depth_ask_down_bid_down = 0;//売板のみが下落した後に買板のみが下落したときの、売板の枚数（2011/2/14以降）

		double down_move_time = 0;//板が下がるまでに所要した時間
		double up_move_time = 0;//板が上がるまでに所要した時間
		int down_move_time_count = 0;//「板が下がるまでに所要した時間」の平均時間求めるために必要な変数
		int up_move_time_count = 0;//「板が上がるまでに所要した時間」の平均時間求めるために必要な変数


		double down_move_time_without_only = 0;//板が下がるまでに所要した時間without_only
		double up_move_time_without_only = 0;//板が上がるまでに所要した時間without_only
		int down_move_time_count_without_only = 0;//「板が下がるまでに所要した時間」の平均時間求めるために必要な変数without_only
		int up_move_time_count_without_only = 0;//「板が上がるまでに所要した時間」の平均時間求めるために必要な変数without_only

		double move_before_time = 0.0;//move_timeを求める際に使う変数
		double move_before_time_without_only = 0.0;//move_timeを求める際に使う変数without_only

		int move_market_count = 0;//marketで遷移した個数
		int move_cancel_count = 0;//cancelで遷移した個数
		int move_cancel_after_market_count = 0;//cancel after marketで遷移した個数


		boolean ask_up_bid_up = false;
		boolean bid_down_ask_down = false;

		boolean morning_or_afternoon = false;//前場(false) or 後場(true)



		while((txtFileName = br.readLine()) != null) {

			String day = null;



			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("\\.");

			File file_up = new File(filename[0] + "_up_count.csv");
			PrintWriter pw_up = new PrintWriter(new BufferedWriter(new FileWriter(file_up)));
			File file_down = new File(filename[0] + "_down_count.csv");
			PrintWriter pw_down = new PrintWriter(new BufferedWriter(new FileWriter(file_down)));

			File file_time = new File(filename[0] + "_move_time.csv");
			PrintWriter pw_time = new PrintWriter(new BufferedWriter(new FileWriter(file_time)));

			pw_up.println("day,divide,up or down,up_count,up_bid_depth_count,up_ask_depth_count,up_count_dif_not_10,up_count_only_bid,up_count_only_ask,up_count_ask_up_bid_up,up_bid_depth_ask_up_bid_up,up_ask_depth_ask_up_bid_up,,,,");//ラベルの書き込み
			pw_down.println("day,divide,up or down,down_count,down_bid_depth_count,down_ask_depth_count,down_count_dif_not_10,down_count_only_bid,down_count_only_ask,down_count_ask_down_bid_down,down_bid_depth_ask_down_bid_down,down_ask_depth_ask_down_bid_down,,");//ラベルの書き込み
			pw_time.println("day,divide,up_move_time,up_move_time_without_only,down_move_time,down_move_time_without_only,move_market_count,move_cancel_count,move_cancel_after_market_count");

			String  JNIc_split[] = null;


			while ((line = brtxt.readLine()) != null) {




				JNIc_split = line.split(",", 0);

				if(day == null){
					day = JNIc_split[0];
				}
				else if(!(day.equals(JNIc_split[0]))){
					if(Integer.parseInt(day) < 20110214){
						if(up_count_morning != 0){//前場データの書き込み
							pw_up.println(day + ",morning,up," + up_count_morning + "," + up_bid_depth_count_morning + "," + up_ask_depth_count_morning + "," + up_count_dif_not_10_morning + "," + up_count_only_bid_morning + "," + up_count_only_ask_morning + "," + up_count_ask_up_bid_up_morning + "," + up_bid_depth_ask_up_bid_up_morning + "," + up_ask_depth_ask_up_bid_up_morning);
							pw_down.println(day + ",morning,down," + down_count_morning + "," + down_bid_depth_count_morning + "," + down_ask_depth_count_morning + "," + down_count_dif_not_10_morning + "," + down_count_only_bid_morning + "," + down_count_only_ask_morning + "," + down_count_ask_down_bid_down_morning + "," + down_bid_depth_ask_down_bid_down_morning + "," + down_ask_depth_ask_down_bid_down_morning);
						}
						else if(up_count_morning == 0){//前場データがない場合

							pw_up.println(day + ",morning,up,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,");
							pw_down.println(day + ",morning,down,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
						}

						if(up_count_afternoon != 0){//後場データの書き込み
							pw_up.println(day + ",afternoon,up," + up_count_afternoon + "," + up_bid_depth_count_afternoon + "," + up_ask_depth_count_afternoon + "," + up_count_dif_not_10_afternoon + "," + up_count_only_bid_afternoon + "," + up_count_only_ask_afternoon + "," + up_count_ask_up_bid_up_afternoon + "," + up_bid_depth_ask_up_bid_up_afternoon + "," + up_ask_depth_ask_up_bid_up_afternoon);
							pw_down.println(day + ",afternoon,down," + down_count_afternoon + "," + down_bid_depth_count_afternoon + "," + down_ask_depth_count_afternoon + "," + down_count_dif_not_10_afternoon + "," + down_count_only_bid_afternoon + "," + down_count_only_ask_afternoon + "," + down_count_ask_down_bid_down_afternoon + "," + down_bid_depth_ask_down_bid_down_afternoon + "," + down_ask_depth_ask_down_bid_down_afternoon);
						}
						else if(up_count_afternoon == 0){//後場データがない場合
							pw_up.println(day + ",afternoon,up,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
							pw_down.println(day + ",afternoon,down,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
						}
					}
					else if(20110214 <= Integer.parseInt(day)){//2011/2/14以降
						pw_up.println(day + ",no noon recess,up," + up_count + "," + up_bid_depth_count + "," + up_ask_depth_count + "," + up_count_dif_not_10 + "," + up_count_only_bid + "," + up_count_only_ask + "," + up_count_ask_up_bid_up + "," + up_bid_depth_ask_up_bid_up + "," + up_ask_depth_ask_up_bid_up);
						pw_down.println(day + ",no noon recess,down," + down_count + "," + down_bid_depth_count + "," + down_ask_depth_count + "," + down_count_dif_not_10 + "," + down_count_only_bid + "," + down_count_only_ask + "," +  down_count_ask_down_bid_down + "," + down_bid_depth_ask_down_bid_down + "," + down_ask_depth_ask_down_bid_down);
					}

					//------------------(初期化)----------------------


					//day = JNIc_split[0];はpw_timeがあるため、あえて入れていない
					up_count = 0;
					up_count_morning = 0;
					up_count_afternoon = 0;
					up_bid_depth_count = 0;
					up_bid_depth_count_morning = 0;
					up_bid_depth_count_afternoon = 0;
					up_ask_depth_count = 0;
					up_ask_depth_count_morning = 0;
					up_ask_depth_count_afternoon = 0;

					down_count = 0;
					down_count_morning = 0;
					down_count_afternoon = 0;
					down_bid_depth_count = 0;
					down_bid_depth_count_morning = 0;
					down_bid_depth_count_afternoon = 0;
					down_ask_depth_count = 0;
					down_ask_depth_count_morning = 0;
					down_ask_depth_count_afternoon = 0;
					up_count_dif_not_10_morning = 0;
					up_count_dif_not_10_afternoon = 0;
					up_count_dif_not_10 = 0;

					down_count_dif_not_10_morning = 0;
					down_count_dif_not_10_afternoon = 0;
					down_count_dif_not_10 = 0;

					up_count_only_bid_morning = 0;
					up_count_only_bid_afternoon = 0;
					up_count_only_bid = 0;
					up_count_only_ask_morning = 0;
					up_count_only_ask_afternoon = 0;
					up_count_only_ask = 0;

					down_count_only_bid_morning = 0;
					down_count_only_bid_afternoon = 0;
					down_count_only_bid = 0;
					down_count_only_ask_morning = 0;
					down_count_only_ask_afternoon = 0;
					down_count_only_ask = 0;

					up_count_ask_up_bid_up_morning = 0;
					up_count_ask_up_bid_up_afternoon = 0;
					up_count_ask_up_bid_up = 0;

					up_bid_depth_ask_up_bid_up_morning = 0;
					up_bid_depth_ask_up_bid_up_afternoon = 0;
					up_bid_depth_ask_up_bid_up = 0;
					up_ask_depth_ask_up_bid_up_morning = 0;
					up_ask_depth_ask_up_bid_up_afternoon = 0;
					up_ask_depth_ask_up_bid_up = 0;


					down_count_ask_down_bid_down_morning = 0;
					down_count_ask_down_bid_down_afternoon = 0;
					down_count_ask_down_bid_down = 0;

					down_bid_depth_ask_down_bid_down_morning = 0;
					down_bid_depth_ask_down_bid_down_afternoon = 0;
					down_bid_depth_ask_down_bid_down = 0;
					down_ask_depth_ask_down_bid_down_morning = 0;
					down_ask_depth_ask_down_bid_down_afternoon = 0;
					down_ask_depth_ask_down_bid_down = 0;





					ask_up_bid_up = false;
					bid_down_ask_down = false;
					//------------------(初期化)----------------------

				}

				if(day == null){
					day = JNIc_split[0];
				}
				else if(!(day.equals(JNIc_split[0])) || (45000 <= time_total && Integer.parseInt(day) < 20110214 && morning_or_afternoon == false)){
					if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//半日オークションの場合
						pw_time.println(day + ",morning," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
						morning_or_afternoon = true;
					}
					else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && !(day.equals(JNIc_split[0]))){//前場の書き込み
						pw_time.println(day + ",morning," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
						morning_or_afternoon = false;
					}
					else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
						pw_time.println(day + ",afternoon," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
						morning_or_afternoon = false;

					}
					else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
						pw_time.println(day + ",no noon recess," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
					}
					//------------初期化--------------
					day = JNIc_split[0];
					down_move_time = 0;
					up_move_time = 0;
					down_move_time_count = 0;
					up_move_time_count = 0;
					move_before_time = 0;

					down_move_time_without_only = 0;
					up_move_time_without_only = 0;
					down_move_time_count_without_only = 0;
					up_move_time_count_without_only = 0;
					move_before_time_without_only = 0;

					move_market_count = 0;
					move_cancel_count = 0;
					move_cancel_after_market_count = 0;

					//------------初期化--------------
				}

				hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
				minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
				second = Double.parseDouble(JNIc_split[1].substring(6));
				time_total = hour*3600 + minute*60 + second;

				if((Integer.parseInt(JNIc_split[0]) < 20110214 && (time_total <= 40500 || 45000 <= time_total)) ||
						(20110214 <= Integer.parseInt(JNIc_split[0]))){//昼休みを含まず、9:00-15:10の間のデータを取り除く

					if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){//買板・売板の上昇
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							up_count_morning++;
							up_bid_depth_count_morning += Integer.parseInt(JNIc_split[4]);
							up_ask_depth_count_morning += Integer.parseInt(JNIc_split[6]);
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							up_count_afternoon++;
							up_bid_depth_count_afternoon += Integer.parseInt(JNIc_split[4]);
							up_ask_depth_count_afternoon += Integer.parseInt(JNIc_split[6]);
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							up_count++;
							up_bid_depth_count += Integer.parseInt(JNIc_split[4]);
							up_ask_depth_count += Integer.parseInt(JNIc_split[6]);
						}

						if(move_before_time != 0){
							up_move_time += time_total - move_before_time;
							up_move_time_count++;
							move_before_time = time_total;
						}
						else{
							move_before_time = time_total;
						}

						if(move_before_time_without_only != 0){
							up_move_time_without_only += time_total - move_before_time_without_only;
							up_move_time_count_without_only++;
							move_before_time_without_only = time_total;
						}
						else{
							move_before_time_without_only = time_total;
						}

						//System.out.println(line);
						if(JNIc_split[10].equals("market")){
							move_market_count++;
						}
						else if(JNIc_split[10].equals("cancel")){
							move_cancel_count++;
						}
						else if(JNIc_split[10].equals("cancel after market")){
							move_cancel_after_market_count++;
						}
						else{
							System.out.println(line);
						}

						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}
					else if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){//買板・売板の下落

						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							down_count_morning++;
							down_bid_depth_count_morning += Integer.parseInt(JNIc_split[4]);
							down_ask_depth_count_morning += Integer.parseInt(JNIc_split[6]);
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							down_count_afternoon++;
							down_bid_depth_count_afternoon += Integer.parseInt(JNIc_split[4]);
							down_ask_depth_count_afternoon += Integer.parseInt(JNIc_split[6]);
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							down_count++;
							down_bid_depth_count += Integer.parseInt(JNIc_split[4]);
							down_ask_depth_count += Integer.parseInt(JNIc_split[6]);
						}

						if(move_before_time != 0){
							down_move_time += time_total - move_before_time;
							down_move_time_count++;
							move_before_time = time_total;
						}
						else{
							move_before_time = time_total;
						}

						if(move_before_time_without_only != 0){
							down_move_time_without_only += time_total - move_before_time_without_only;
							down_move_time_count_without_only++;
							move_before_time_without_only = time_total;
						}
						else{
							move_before_time_without_only = time_total;
						}

						if(JNIc_split[10].equals("market")){
							move_market_count++;
						}
						else if(JNIc_split[10].equals("cancel")){
							move_cancel_count++;
						}
						else if(JNIc_split[10].equals("cancel after market")){
							move_cancel_after_market_count++;
						}
						else{
							System.out.println(line);
						}

						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}
					else if(JNIc_split[2].equals("up price dif not 10") || JNIc_split[2].equals("up not Trade not 10")){
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							up_count_dif_not_10_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							up_count_dif_not_10_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							up_count_dif_not_10++;
						}
						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}
					else if(JNIc_split[2].equals("down price dif not 10") || JNIc_split[2].equals("down not Trade not 10")){
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							down_count_dif_not_10_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							down_count_dif_not_10_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							down_count_dif_not_10++;
						}
						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}
					else if(JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade")){//実際のデータに「up only bid」はない。
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							up_count_only_bid_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							up_count_only_bid_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							up_count_only_bid++;
						}
						if(ask_up_bid_up == true){//売板が上昇した後に買板が上昇する場合
							if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
								up_count_ask_up_bid_up_morning++;
								up_bid_depth_ask_up_bid_up_morning += Integer.parseInt(JNIc_split[4]);
								up_ask_depth_ask_up_bid_up_morning += Integer.parseInt(JNIc_split[6]);
							}
							else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
								up_count_ask_up_bid_up_afternoon++;
								up_bid_depth_ask_up_bid_up_afternoon += Integer.parseInt(JNIc_split[4]);
								up_ask_depth_ask_up_bid_up_afternoon += Integer.parseInt(JNIc_split[6]);
							}
							else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
								up_count_ask_up_bid_up++;
								up_bid_depth_ask_up_bid_up += Integer.parseInt(JNIc_split[4]);
								up_ask_depth_ask_up_bid_up += Integer.parseInt(JNIc_split[6]);
							}

							if(move_before_time != 0){
								up_move_time += time_total - move_before_time;
								up_move_time_count++;
								move_before_time = time_total;
							}
							else{
								move_before_time = time_total;
							}



						}
						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}

					else if(JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade")){
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							up_count_only_ask_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							up_count_only_ask_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							up_count_only_ask++;
						}

						ask_up_bid_up = true;
						bid_down_ask_down = false;

						if(JNIc_split[10].equals("market")){
							move_market_count++;
						}
						else if(JNIc_split[10].equals("cancel")){
							move_cancel_count++;
						}
						else if(JNIc_split[10].equals("cancel after market")){
							move_cancel_after_market_count++;
						}
						else{
							System.out.println(line);
						}
					}
					else if(JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade")){
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							down_count_only_bid_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							down_count_only_bid_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							down_count_only_bid++;
						}

						ask_up_bid_up = false;
						bid_down_ask_down = true;

						if(JNIc_split[10].equals("market")){
							move_market_count++;
						}
						else if(JNIc_split[10].equals("cancel")){
							move_cancel_count++;
						}
						else if(JNIc_split[10].equals("cancel after market")){
							move_cancel_after_market_count++;
						}
						else{
							System.out.println(line);
						}
					}
					else if(JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade")){//実際のデータに「down only ask」はない。
						if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
							down_count_only_ask_morning++;
						}
						else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
							down_count_only_ask_afternoon++;
						}
						else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
							down_count_only_ask++;
						}
						if(bid_down_ask_down == true){//買板が下落した後に売板が下落する場合
							//System.out.println(line);
							if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
								down_count_ask_down_bid_down_morning++;
								down_bid_depth_ask_down_bid_down_morning += Integer.parseInt(JNIc_split[4]);
								down_ask_depth_ask_down_bid_down_morning += Integer.parseInt(JNIc_split[6]);
							}
							else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
								down_count_ask_down_bid_down_afternoon++;
								down_bid_depth_ask_down_bid_down_afternoon += Integer.parseInt(JNIc_split[4]);
								down_ask_depth_ask_down_bid_down_afternoon += Integer.parseInt(JNIc_split[6]);
							}
							else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
								down_count_ask_down_bid_down++;
								down_bid_depth_ask_down_bid_down += Integer.parseInt(JNIc_split[4]);
								down_ask_depth_ask_down_bid_down += Integer.parseInt(JNIc_split[6]);
							}

							if(move_before_time != 0){
								down_move_time += time_total - move_before_time;
								down_move_time_count++;
								move_before_time = time_total;
							}
							else{
								move_before_time = time_total;
							}



						}
						ask_up_bid_up = false;
						bid_down_ask_down = false;
					}
					else{
						//System.out.println(line);
					}

					if(JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("down only ask")){
						System.out.println(line);
					}

				}





			}

			//------------------------------------最後の書き込み-----------------------------------------------
			if(Integer.parseInt(day) < 20110214){
				if(up_count_morning != 0){//前場データの書き込み
					pw_up.println(day + ",morning,up," + up_count_morning + "," + up_bid_depth_count_morning + "," + up_ask_depth_count_morning + "," + up_count_dif_not_10_morning + "," + up_count_only_bid_morning + "," + up_count_only_ask_morning + "," + up_count_ask_up_bid_up_morning + "," + up_bid_depth_ask_up_bid_up_morning + "," + up_ask_depth_ask_up_bid_up_morning + "," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only);
					pw_down.println(day + ",morning,down," + down_count_morning + "," + down_bid_depth_count_morning + "," + down_ask_depth_count_morning + "," + down_count_dif_not_10_morning + "," + down_count_only_bid_morning + "," + down_count_only_ask_morning + "," + down_count_ask_down_bid_down_morning + "," + down_bid_depth_ask_down_bid_down_morning + "," + down_ask_depth_ask_down_bid_down_morning + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only);
				}
				else if(up_count_morning == 0){//前場データがない場合

					pw_up.println(day + ",morning,up,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,");
					pw_down.println(day + ",morning,down,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
				}

				if(up_count_afternoon != 0){//後場データの書き込み
					pw_up.println(day + ",afternoon,up," + up_count_afternoon + "," + up_bid_depth_count_afternoon + "," + up_ask_depth_count_afternoon + "," + up_count_dif_not_10_afternoon + "," + up_count_only_bid_afternoon + "," + up_count_only_ask_afternoon + "," + up_count_ask_up_bid_up_afternoon + "," + up_bid_depth_ask_up_bid_up_afternoon + "," + up_ask_depth_ask_up_bid_up_afternoon + "," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only);
					pw_down.println(day + ",afternoon,down," + down_count_afternoon + "," + down_bid_depth_count_afternoon + "," + down_ask_depth_count_afternoon + "," + down_count_dif_not_10_afternoon + "," + down_count_only_bid_afternoon + "," + down_count_only_ask_afternoon + "," + down_count_ask_down_bid_down_afternoon + "," + down_bid_depth_ask_down_bid_down_afternoon + "," + down_ask_depth_ask_down_bid_down_afternoon + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only);
				}
				else if(up_count_afternoon == 0){//後場データがない場合
					pw_up.println(day + ",afternoon,up,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
					pw_down.println(day + ",afternoon,down,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,,,,");
				}
			}
			else if(20110214 <= Integer.parseInt(day)){//2011/2/14以降
				pw_up.println(day + ",no noon recess,up," + up_count + "," + up_bid_depth_count + "," + up_ask_depth_count + "," + up_count_dif_not_10 + "," + up_count_only_bid + "," + up_count_only_ask + up_count_ask_up_bid_up + "," + up_bid_depth_ask_up_bid_up + "," + up_ask_depth_ask_up_bid_up + "," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only);
				pw_down.println(day + ",no noon recess,down," + down_count + "," + down_bid_depth_count + "," + down_ask_depth_count + "," + down_count_dif_not_10 + "," + down_count_only_bid + "," + down_count_only_ask + "," +  down_count_ask_down_bid_down + "," + down_bid_depth_ask_down_bid_down + "," + down_ask_depth_ask_down_bid_down + "," + down_move_time/down_move_time_count + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only);
			}




			if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//半日オークションの場合
				pw_time.println(day + ",morning," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
				morning_or_afternoon = true;
			}
			else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && !(day.equals(JNIc_split[0]))){//前場の書き込み
				pw_time.println(day + ",morning," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
				morning_or_afternoon = false;
			}
			else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
				pw_time.println(day + ",afternoon," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
				morning_or_afternoon = false;

			}
			else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
				pw_time.println(day + ",no noon recess," + up_move_time/up_move_time_count + "," + up_move_time_without_only/up_move_time_count_without_only + "," + down_move_time/down_move_time_count + "," + down_move_time_without_only/down_move_time_count_without_only + "," + move_market_count + "," + move_cancel_count+ "," + move_cancel_after_market_count);
			}
			//------------初期化--------------
			day = JNIc_split[0];
			down_move_time = 0;
			up_move_time = 0;
			down_move_time_count = 0;
			up_move_time_count = 0;
			move_before_time = 0;

			down_move_time_without_only = 0;
			up_move_time_without_only = 0;
			down_move_time_count_without_only = 0;
			up_move_time_count_without_only = 0;
			move_before_time_without_only = 0;

			move_market_count = 0;
			move_cancel_count = 0;
			move_cancel_after_market_count = 0;





			//------------------------------------最後の書き込み-----------------------------------------------



			//---------------------------------------初期化-------------------------------------------
			day = null;

			up_count = 0;
			up_count_morning = 0;
			up_count_afternoon = 0;
			up_bid_depth_count = 0;
			up_bid_depth_count_morning = 0;
			up_bid_depth_count_afternoon = 0;
			up_ask_depth_count = 0;
			up_ask_depth_count_morning = 0;
			up_ask_depth_count_afternoon = 0;

			down_count = 0;
			down_count_morning = 0;
			down_count_afternoon = 0;
			down_bid_depth_count = 0;
			down_bid_depth_count_morning = 0;
			down_bid_depth_count_afternoon = 0;
			down_ask_depth_count = 0;
			down_ask_depth_count_morning = 0;
			down_ask_depth_count_afternoon = 0;
			up_count_dif_not_10_morning = 0;
			up_count_dif_not_10_afternoon = 0;
			up_count_dif_not_10 = 0;

			down_count_dif_not_10_morning = 0;
			down_count_dif_not_10_afternoon = 0;
			down_count_dif_not_10 = 0;

			up_count_only_bid_morning = 0;
			up_count_only_bid_afternoon = 0;
			up_count_only_bid = 0;
			up_count_only_ask_morning = 0;
			up_count_only_ask_afternoon = 0;
			up_count_only_ask = 0;

			down_count_only_bid_morning = 0;
			down_count_only_bid_afternoon = 0;
			down_count_only_bid = 0;
			down_count_only_ask_morning = 0;
			down_count_only_ask_afternoon = 0;
			down_count_only_ask = 0;

			up_count_ask_up_bid_up_morning = 0;
			up_count_ask_up_bid_up_afternoon = 0;
			up_count_ask_up_bid_up = 0;

			up_bid_depth_ask_up_bid_up_morning = 0;
			up_bid_depth_ask_up_bid_up_afternoon = 0;
			up_bid_depth_ask_up_bid_up = 0;
			up_ask_depth_ask_up_bid_up_morning = 0;
			up_ask_depth_ask_up_bid_up_afternoon = 0;
			up_ask_depth_ask_up_bid_up = 0;


			down_count_ask_down_bid_down_morning = 0;
			down_count_ask_down_bid_down_afternoon = 0;
			down_count_ask_down_bid_down = 0;

			down_bid_depth_ask_down_bid_down_morning = 0;
			down_bid_depth_ask_down_bid_down_afternoon = 0;
			down_bid_depth_ask_down_bid_down = 0;
			down_ask_depth_ask_down_bid_down_morning = 0;
			down_ask_depth_ask_down_bid_down_afternoon = 0;
			down_ask_depth_ask_down_bid_down = 0;

			down_move_time = 0;
			up_move_time = 0;
			down_move_time_count = 0;
			up_move_time_count = 0;
			move_before_time = 0;

			down_move_time_without_only = 0;
			up_move_time_without_only = 0;
			down_move_time_count_without_only = 0;
			up_move_time_count_without_only = 0;
			move_before_time_without_only = 0;

			move_market_count = 0;
			move_cancel_count = 0;
			move_cancel_after_market_count = 0;


			ask_up_bid_up = false;
			bid_down_ask_down = false;
			//---------------------------------------初期化-------------------------------------------








			brtxt.close();
			fr.close();
			pw_up.close();
			pw_down.close();
			pw_time.close();




		}

		br.close();
	}
}

