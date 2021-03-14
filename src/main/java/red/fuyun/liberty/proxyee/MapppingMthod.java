package red.fuyun.liberty.proxyee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import red.fuyun.liberty.mapper.LeaveMapper;
import red.fuyun.liberty.pojo.Leave;
import red.fuyun.liberty.pojo.User;
import red.fuyun.liberty.service.LeaveService;
import red.fuyun.liberty.service.UserService;
import red.fuyun.liberty.util.HttpUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class MapppingMthod {
    @Autowired
    HttpUtil httpUtil;

    @Autowired
    UserService userService;


    @Autowired
    LeaveService leaveService;

    private  Map<String,Cell<HttpRequest, FullHttpResponse, HttpProxyInterceptPipeline, Leave>> map = new HashMap<>();
//             System.out.println("处理假条列表");
//    String content = httpResponse.content().toString(Charset.defaultCharset());
//    String con = "{\"code\":\"0\",\"message\":\"success\",\"datas\":{\"totalSize\":5,\"pageSize\":20,\"pageNumber\":1,\"allowApply\":0,\"allowPcApply\":0,\"rows\":[]}}";
//    JSONObject jsonObject = JSONObject.parseObject(con);
//    JSONObject data = jsonObject.getJSONObject("datas");
//    JSONArray rows = data.getJSONArray("rows");
//    JSONObject row = JSONObject.parseObject("{\"id\":\"3659a5b6515f4efbb9c1780777b69668\",\"leaveType\":\"事假\",\"createTime\":\"03-08 15:35\",\"startTime\":\"03-13 00:00\",\"endTime\":\"03-13 22:34\",\"totalDay\":1,\"status\":\"6\",\"expireDay\":null,\"isExtend\":\"0\",\"applyTime\":\"03-08 15:35\",\"out\":\"1\",\"leaveTime\":\"22小时34分钟\",\"startTimePC\":\"03-13 00:00\",\"endTimePC\":\"03-13 22:34\",\"leaveReason\":\"周六教师资格证考试。\",\"shutDown\":false,\"actEndTime\":\"03-13 22:34\",\"actEndTimeDesc\":\"22小时34分钟\",\"actStatus\":\"6\",\"isEarlyEnd\":\"0\"}");
//            rows.add(row);
//    LocalDateTime now = LocalDateTime.now();
//    LocalDateTime startTime = now.minusHours(1);
//    LocalDateTime endTime = now.plusHours(6);
//    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
//            row.put("startTime",startTime.format(dateTimeFormatter));
//            row.put("endTime",endTime.format(dateTimeFormatter));
//            httpResponse.content().clear();
//            httpResponse.headers().set("Content-Type","application/json;charset=UTF-8");
//            System.out.println(jsonObject.toJSONString());
//            httpResponse.content().writeBytes(jsonObject.toString().getBytes());
//            return true;
//
    @PostConstruct
    private  void init(){
        map.put("/wec-counselor-leave-apps/leave/stu/list", (httpRequest, httpResponse, httpProxyIntercepts,leave) -> {
            System.out.println("处理假条列表");
            String content = httpResponse.content().toString(Charset.defaultCharset());
            System.out.println(content);
            JSONObject jsonObject = JSONObject.parseObject(content);
            JSONObject datas = jsonObject.getJSONObject("datas");
            Integer totalSize = datas.getInteger("totalSize");
            if (totalSize<=0){
                return false;
            }
            JSONArray rows = datas.getJSONArray("rows");
            JSONObject row = rows.getJSONObject(0);
            rows.clear();
            rows.add(row);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = leave.getStartTime().toLocalDateTime();
            LocalDateTime endTime = leave.getEndTime().toLocalDateTime();
            boolean after = endTime.isAfter(now);
            if (!after){
                startTime = LocalDateTime.of(now.toLocalDate(),startTime.toLocalTime());
                endTime = LocalDateTime.of(now.toLocalDate(),endTime.toLocalTime());
            }

            Duration duration = Duration.between(startTime,endTime);
            long hours = duration.toHours();//相差的小时数
            long minutes = duration.toMinutes()-hours*60;//相差的分钟数
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            row.put("startTime",startTime.format(dateTimeFormatter));
            row.put("endTime",endTime.format(dateTimeFormatter));
            row.put("status",6);
            row.put("actStatus",6);
            row.put("leaveTime",String.format("%d小时%d分钟",hours,minutes));
            httpResponse.content().clear();
            httpResponse.headers().set("Content-Type","application/json;charset=UTF-8");
            System.out.println(jsonObject.toJSONString());
            httpResponse.content().writeBytes(jsonObject.toString().getBytes());
            return true;
        });

        map.put("/wec-counselor-leave-apps/leave/stu/detail",(httpRequest, httpResponse, httpProxyIntercepts,leave) -> {

            System.out.println("处理假条详情");
            String content = httpResponse.content().toString(Charset.defaultCharset());
            JSONObject jsonObject = JSONObject.parseObject(content, Feature.OrderedField);
            JSONObject datas = jsonObject.getJSONObject("datas");
            JSONObject termination = datas.getJSONObject("termination");
            termination.put("terminationObjectName",null);
            termination.put("terminationReason",null);
            termination.put("terminationDate",null);

            JSONObject condition = datas.getJSONObject("condition");
            condition.put("reportTutorialUrl","http://catqa.cpdaily.com/2018/10/24/如何销假？/");
            condition.put("reportTutorialTitle","如何销假？");
            datas.put("recordStatus",6);
            datas.put("isAllowedExtend",true);
            datas.put("isReport","1");
            datas.put("isExtend","1");

            JSONObject detail = datas.getJSONObject("detail");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = leave.getStartTime().toLocalDateTime();
            LocalDateTime endTime = leave.getEndTime().toLocalDateTime();

            boolean after = endTime.isAfter(now);
            if (!after){
                startTime = LocalDateTime.of(now.toLocalDate(),startTime.toLocalTime());
                endTime = LocalDateTime.of(now.toLocalDate(),endTime.toLocalTime());
            }

            Random rand = new Random();
            LocalDateTime applyTime = startTime.minusDays(1).plusMinutes(rand.nextInt(25)+1);

            DateTimeFormatter time = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Duration duration = Duration.between(startTime,endTime);
            long hours = duration.toHours();//相差的小时数
            long minutes = duration.toMinutes()-hours*60;//相差的分钟数
            JSONArray approvers = detail.getJSONArray("approvers");
            JSONArray applyAttach = detail.getJSONArray("applyAttach");
            applyAttach.clear();
            Iterator<Object> iterator = approvers.iterator();

            while (iterator.hasNext()){
                JSONObject next = (JSONObject)iterator.next();
                if (next.getInteger("status").equals(5)){
                    iterator.remove();
                }
                next.put("createTime",applyTime.format(time));
                next.put("approveOption","");

                applyTime = applyTime.plusHours(rand.nextInt(2)+1).plusMinutes(rand.nextInt(50)+1);
            }

            JSONArray logInfos = detail.getJSONArray("logInfos");
            Iterator<Object> logit = logInfos.iterator();
            while (logit.hasNext()){
                JSONObject next = (JSONObject)logit.next();
                if (next.getInteger("status").equals(5)){
                    logit.remove();
                }

            }
            detail.put("startTime",startTime.format(time));
            detail.put("endTime",endTime.format(time));
            detail.put("startDate",startTime.format(date));
            detail.put("endDate",endTime.format(date));
            detail.put("status",2);
            detail.put("actStatus",6);
            detail.put("dstStatus",6);
            detail.put("isEarlyEnd",0);
            detail.put("urgencyMobile",leave.getUrgencyMobile());
            detail.put("leaveReason",leave.getLeaveReason());
            detail.put("totalDays",String.format("%d小时%d分钟",hours,minutes));
            datas.put("nowTime",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            httpResponse.content().clear();
            httpResponse.headers().set("Content-Type","application/json;charset=UTF-8");
            System.out.println(jsonObject.toJSONString());
            httpResponse.content().writeBytes(jsonObject.toString().getBytes());
            System.out.println(content);
            return true;
        });


    }
    public  CellWarpper get(String key){
        Cell cell = map.get(key);
        CellWarpper cellWarpper = new CellWarpper(cell);
        return cellWarpper;
    }

    class CellWarpper {
        private Cell cell;
        CellWarpper(Cell cell){
            this.cell = cell;
        }

        protected Leave before(HttpRequest httpRequest) throws IOException {
            HttpHeaders headers = httpRequest.headers();
            String uri  = "/wec-counselor-leave-apps/leave/stu/getStuBasicInfo";
            String origin = headers.get("Origin");
            String url = origin+uri;
            String cookie = headers.get("Cookie");
            String contentType = headers.get("Content-Type");
            Map<String,String> map = new HashMap<>();
            map.put("origin",origin);
            map.put("cookie",cookie);
            JSONObject jsonObject = httpUtil.postJson(url, new JSONObject(), map);
            JSONObject datas = jsonObject.getJSONObject("datas");
            String userId = datas.getString("userId");
            User user = userService.queryStudentNo(userId);
            if (Objects.isNull(user)){
                return null;
            }
            Leave leave = leaveService.queryByUId(user.getId());

            return leave;
        }

        protected void after(){

        }

        public boolean apply(HttpRequest httpRequest, FullHttpResponse fullHttpResponse, HttpProxyInterceptPipeline httpProxyIntercepts) throws IOException {
            Leave leave = before(httpRequest);
            if (Objects.isNull(leave)){
                return false;
            }
            return cell.apply(httpRequest, fullHttpResponse, httpProxyIntercepts,leave);
        }

    }



    public boolean container(String key){
        return map.containsKey(key);
    }
//    yyyy-MM-dd HH:mm:ss
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minusHours(1);
        LocalDateTime endTime = now.plusHours(6);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

        System.out.println(now);
        System.out.println(startTime.format(dateTimeFormatter));
        System.out.println(endTime.format(dateTimeFormatter));

    }
}

