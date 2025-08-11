/* -------------------------------------------------------------------------- */
/*                View to get users with their respective role                */
/* -------------------------------------------------------------------------- */
CREATE OR REPLACE view v_users AS
    SELECT 
        u.*,
        r."label" AS role
    FROM _user_ u
    JOIN user_role ur
        ON ur.user_id = u.id
    JOIN _role_ r
        ON ur.role_id = r.id
;
