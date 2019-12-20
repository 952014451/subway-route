public class subway{
    public static void main(String args[]){
        try {
            if(args.length==2&&args[0].equals("-map")){
                System.out.println(new calculate().allWays(args[1]));
            }
            else if(args.length==6&&args[0].equals("-a")&&args[2].equals("-map")){
                new calculate().oneWay(args[1],args[3],args[5]);
            }
            else if(args.length==7&&args[0].equals("-b")&&args[3].equals("-map")){
                new calculate().toWay(args[1],args[2],args[4],args[6]);
            }
            else{
                System.out.println("你的输入有误！");
            }
        }catch (Exception e){
            System.out.println("没有该线路！");
            e.printStackTrace();}
    }
}