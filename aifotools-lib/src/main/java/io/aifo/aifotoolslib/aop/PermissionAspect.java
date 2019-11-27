package io.aifo.aifotoolslib.aop;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import com.blankj.utilcode.util.ActivityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import io.aifo.aifotoolslib.utils.PermissionUtils;
import io.aifo.api.aspect.Permission;


@Aspect
public class PermissionAspect {

    @Around("execution(@io.aifo.api.aspect.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {

        FragmentActivity ac = (FragmentActivity) ActivityUtils.getTopActivity();
        PermissionUtils.requestPermissions(ac, permission.value(), isOk -> {
            if (isOk) {
                try {
                    System.out.println("----------------------isOk-------------------------"+isOk);
                    joinPoint.proceed();//获得权限，执行原方法
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else {
                showTipsDialog(ac);
            }
        });
    }

    /**
     * 显示提示对话框
     */
    public static void showTipsDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
