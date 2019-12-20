import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class calculate {
    InputStream input=null;
    OutputStream out=null;
    public calculate(){};

    //需求一
    public String allWays(String way) throws Exception{
        File file=new File(way);
        Reader reader=new FileReader(file);
        int len=0;
        char c[]=new char[5000];
        int temp=0;
        while((temp=reader.read())!=-1){
            c[len]=(char)temp;
            len++;
        }
        reader.close();
        return new String(c,0,len);
    }

    //需求二
    public void oneWay(String way,String input,String out) throws Exception{
        int index=0;
        File file1=new File(input);
        File file2=new File(out);
        InputStream input1=new FileInputStream(file1);
        OutputStream out1=new FileOutputStream(file2);
        byte b[]=new byte[(int)file1.length()];
        input1.read(b);
        input1.close();
        String str=new String(b);
        String s[]=new String[6];
        s=str.split("\\n");
        int c=way.charAt(0)-48;//将字符转换成数字
        for(index=0;index<s.length;index++){
            if (s[index].charAt(0)-48==c){
                c=index;
            }
        }

        StringBuffer buffer=new StringBuffer();
        buffer.append(s[c]);
        out1.write(new String(buffer).getBytes());
        System.out.print(buffer);
        out1.close();
    }

    //需求三
    public void toWay(String start,String end,String input,String out) throws Exception{
        File file1=new File(input);
        File file2=new File(out);
        InputStream input1=new FileInputStream(file1);
        OutputStream out1=new FileOutputStream(file2);
        byte b[]=new byte[(int)file1.length()];
        input1.read(b);
        input1.close();
        String str=new String(b);
        String s[]=new String[6];
        s=str.split("\\n");

        StringBuffer buffer=new StringBuffer();
        if(flag(s,start,end)){
            String ss[]=oneFind(s,start,end);
            buffer.append(ss.length+"\n");
            for (String x:ss) {
                buffer.append(x+"\n");
            }
        }
        else{
            List<List<String>> listto=findway(s,start,end);
            if (listto.size()<2){
                for (List<String> list:listto
                ) {
                    buffer.append(change(list).size()-1 + "\n");
                    for (String x : change(list)) {
                        buffer.append(x + "\n");
                    }
                }
            }else{
                if(listto.get(0).size()>listto.get(1).size()){
                    buffer.append(change(listto.get(1)).size()-1 + "\n");
                    for (String x1:change(listto.get(1))
                         ) {
                        buffer.append(x1 + "\n");
                    }
                }else {
                    buffer.append(change(listto.get(0)).size()-1 + "\n");
                    for (String x1:change(listto.get(0))
                    ) {
                        buffer.append(x1 + "\n");
                    }
                }
            }
        }

        out1.write(new String(buffer).getBytes());
        System.out.print(buffer);
        out1.close();
    }

    //判断是否为同一线路上的两站
    public boolean flag(String s[],String start,String end){
        boolean flag=false;
        for (int i=0;i<s.length;i++) {
            if(s[i].contains(start)&&s[i].contains(end)){
                flag=true;
            }
        }
        return flag;
    }

    //返回所有站点或换乘站点
    public List<String> pointNums(String str[],int arg){
        List<String> list0 = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i=0;i<str.length;i++){
            String newstr[]=str[i].split(" ");
            for(int j=1;j<newstr.length;j++) {
                if (!list0.contains(newstr[j])) {
                    list0.add(newstr[j]);
                }
                else{
                    list1.add(newstr[j]);
                }
            }
        }
        if (arg==0){
            return list0;//返回所有站
        }else{
            return list1;//返回所有换乘站
        }
    }

    //字符串数组转换成list
    public List<String> list(String[] s){
        List<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            arrayList.add(s[i]);
        }
        return change(arrayList);
    }

    //两个站点之间的站 中转一战
    public List<List<String>> findway(String str[], String start, String end){
        String ways[]={"1号线","2号线","3号线","5号线","6号线","9号线"};
        List<List<String>> lists=new ArrayList<List<String>>();
        List<String> list1=change(transferTree(str,start));
        List<String> list2=change(transferTree(str,end));
        int z=0;
        for (int i=0;i<list1.size();i++){
            if (list2.contains(list1.get(i))){
                for (int j=0;j<list2.size();j++){
                    if (list1.get(i).equals(list2.get(j))){
                        lists.add(change(list(oneFind(str,start,list1.get(i)))));
                        for (int c=0;c<str.length;c++) {
                            if(str[c].contains(start)&&str[c].contains(end))break;
                            if (str[c].contains(list1.get(i))&&str[c].contains(end)) {
                                lists.get(z).add(ways[c]);
                                break;
                            }
                        }
                        lists.get(z).addAll(list(oneFind(str,list1.get(i),end)));
                        z++;
                    }
                }
            }else {
                //lists.add(change(transferTree(str,list1.get(i))));
            }
        }
        return lists;
    };

    //同一线路两个站点间的所有站点
    public String[] oneFind(String str[],String point1,String point2){
        String a[][]=ways(str);String s[];
        for (int i=0;i<str.length;i++) {
            if (str[i].contains(point1)&&str[i].contains(point2)){
                int p1=index(str,point1,i);
                int p2=index(str,point2,i);
                if (p1<p2){
                    s=new String[p2-p1+1];
                    int z=0;
                    for (int j=p1;j<=p2;j++) {
                        s[z]=a[i][j];
                        z++;
                    }
                }else{
                    s=new String[p1-p2+1];
                    int z=0;
                    for (int j=p1;j>=p2;j--){
                        s[z]=a[i][j];
                        z++;
                    }
                }
                return s;
            }
        }
        return null;
    }

    //剔除重复换乘站点
    public List<String> change(List<String> list){
        List<String> newlist=new ArrayList<>();
        for(int j=0;j<list.size();j++) {
            if (!newlist.contains(list.get(j))) {
                newlist.add(list.get(j));
            }
        }
        return newlist;
    }

    //查找地铁路线最近换乘站点，并形成集合
    public List<String> transferTree(String str[], String start){
        List<String> list=pointNums(str,1);//所有换乘站
        List<String> transfer=new ArrayList<>();
        if(!transfer.isEmpty()){
            transfer.clear();
        }
        String a[][]=ways(str);
        for (int i=0;i<str.length;i++){
            boolean flag1=true,flag2=true;
            if(str[i].indexOf(start)!=-1){
                int next=index(str,start,i)+1;
                int forward=index(str,start,i)-1;
                for (int j=next;j<a[i].length;j++){
                    for (int z=0;z<list.size();z++){
                        if (a[i][j].equals(list.get(z))&&flag1){
                            transfer.add(list.get(z));
                            flag1=false;
                        }
                    }
                }
                for (int j=forward;j>=0;j--){
                    for (int z=0;z<list.size();z++){
                        if (a[i][j].equals(list.get(z))&&flag2){
                            transfer.add(list.get(z));
                            flag2=false;
                        }
                    }
                }
            }
        }
        return transfer;
    }

    //返回二维地铁路线数组
    public String[][] ways(String str[]){
        String s[][]=new String[6][40];
        for (int i=0;i<str.length;i++){
            s[i]=str[i].substring(2,str[i].length()).split(" ");
        }
        return s;
    }

    //返回所查找的站点在某线路上的位置
    public int index(String str[],String key,int wayindex){
        String s[][]=ways(str);
        int index=0;
        for (int j=0;j<s[wayindex].length;j++){
            if (s[wayindex][j].equals(key)) {
                index = j;
            }
        }
        return index;
    }

}