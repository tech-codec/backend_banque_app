package org.sid.banquetechcodec.web;

import org.sid.banquetechcodec.data.ResetPasswordData;
import org.sid.banquetechcodec.exception.InvalidTokenException;
import org.sid.banquetechcodec.exception.UnkownIdentifierException;
import org.sid.banquetechcodec.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

@CrossOrigin("*")
@Controller
@RequestMapping(value = "/password")
public class PasswordResetControllerMVC {

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String MSG = "resetPasswordMsg";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserServiceI customerAccountService;

    @GetMapping("/change")
    public String changePassword(@RequestParam(required = false) String token, final RedirectAttributes redirAttr, final Model model) {
        if (StringUtils.isEmpty(token)) {
            redirAttr.addFlashAttribute("tokenError",
                    messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale())
            );
            return "votre est inactive ";
        }

        ResetPasswordData data = new ResetPasswordData();
        data.setToken(token);
        setResetPasswordForm(model, data);

        return "account/changePassword";
    }


    @PostMapping("/change")
    public String changePassword(final ResetPasswordData data, final Model model) {
        try {
            customerAccountService.updatePassword(data.getPassword(), data.getToken());
            System.out.println(data.getPassword());
        } catch (InvalidTokenException | UnkownIdentifierException e) {
            // log error statement
            model.addAttribute("tokenError",
                    messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale())
            );

            return "account/changePassword";
        }
        model.addAttribute("passwordUpdateMsg",
                messageSource.getMessage("user.password.updated.msg", null, LocaleContextHolder.getLocale())
        );
        setResetPasswordForm(model, new ResetPasswordData());
        return "account/changePassword";
    }

    private void setResetPasswordForm(final Model model, ResetPasswordData data){
        model.addAttribute("forgotPassword",data);
    }

}
