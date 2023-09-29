package com.xmies.Library.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Expressions {

    @Pointcut("execution(* *.initBinder(..))")
    public void forAllInitBinders() {};

    @Pointcut("execution(* com.xmies.Library.controller.UserLibraryController.menu(..))")
    public void forMenu() {};

    @Pointcut("execution(* com.xmies.Library.controller.UserLibraryController.getBooksList(..))")
    public void forBookList() {};

    @Pointcut("execution(* com.xmies.Library.controller.UserLibraryController.getAuthorsList(..))")
    public void forAuthorList() {};

    @Pointcut("execution(* com.xmies.Library.controller.AdminLibraryController.*(..))")
    public void forAdminLibraryController() {};

    @Pointcut("execution(* com.xmies.Library.controller.UserLibraryController.*(..))")
    public void forUserLibraryController() {};


}
