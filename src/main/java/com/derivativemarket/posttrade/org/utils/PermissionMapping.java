package com.derivativemarket.posttrade.org.utils;

import com.derivativemarket.posttrade.org.entities.enums.Permission;
import com.derivativemarket.posttrade.org.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.derivativemarket.posttrade.org.entities.enums.Permission.*;
import static com.derivativemarket.posttrade.org.entities.enums.Role.*;


public class PermissionMapping {
    private static Map<Role, Set<Permission>> map=Map.of(Developer,Set.of(TRADE_VIEW,COMPANY_VIEW),
    QA,Set.of(TRADE_VIEW,COMPANY_VIEW),
    BA,Set.of(TRADE_REUPLOAD,COMPANY_UPDATE,COMPANY_CREATE),
            FundManager,Set.of( TRADE_VIEW,
                    TRADE_DISPUTE,
                    TRADE_REUPLOAD,
                    TRADE_TERMINATE,
                    COMPANY_VIEW
            )
            );
    public static Set<SimpleGrantedAuthority> getAuthorityForRole(Role role){
        return map.get(role).stream().
                map(permission -> new SimpleGrantedAuthority(permission.name())).
                collect(Collectors.toSet());
    }
}
