package lift.majiang.community.advice;

import lift.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex,Model model){
        //如果异常属于预料范围可处理的 就进入if，如果出现无法处理的等等 就直接"服务错误"
        if (ex instanceof CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }else {
            model.addAttribute("message", "服务错误");
        }
//        HttpStatus status;

        //返回erroe页面
        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request){
//        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
//    }

}
