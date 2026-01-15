-- AccessFlow Seed Data Migration
-- Inserts system roles and standard permissions

-- System Roles
INSERT INTO roles (id, name, description, is_system_role, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'SUPER_ADMIN', 'Super administrator with full system access', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'ORG_OWNER', 'Organization owner with full organization access', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'ORG_ADMIN', 'Organization administrator with management access', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'ORG_MEMBER', 'Standard organization member with basic access', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'ORG_VIEWER', 'Read-only organization viewer', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Standard Permissions

-- User permissions
INSERT INTO permissions (id, permission_key, description, resource, action, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'users:create', 'Create new users', 'users', 'create', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'users:read', 'View user information', 'users', 'read', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'users:update', 'Update user information', 'users', 'update', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'users:delete', 'Delete users', 'users', 'delete', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'users:*', 'All user operations', 'users', '*', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Organization permissions
INSERT INTO permissions (id, permission_key, description, resource, action, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'orgs:create', 'Create new organizations', 'orgs', 'create', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'orgs:read', 'View organization information', 'orgs', 'read', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'orgs:update', 'Update organization information', 'orgs', 'update', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'orgs:delete', 'Delete organizations', 'orgs', 'delete', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'orgs:invite', 'Invite users to organization', 'orgs', 'invite', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'orgs:*', 'All organization operations', 'orgs', '*', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Role permissions
INSERT INTO permissions (id, permission_key, description, resource, action, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'roles:create', 'Create new roles', 'roles', 'create', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'roles:read', 'View role information', 'roles', 'read', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'roles:update', 'Update role information', 'roles', 'update', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'roles:delete', 'Delete roles', 'roles', 'delete', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'roles:assign', 'Assign roles to users', 'roles', 'assign', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'roles:*', 'All role operations', 'roles', '*', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Permission permissions
INSERT INTO permissions (id, permission_key, description, resource, action, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'permissions:read', 'View permission information', 'permissions', 'read', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'permissions:assign', 'Assign permissions to roles', 'permissions', 'assign', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'permissions:*', 'All permission operations', 'permissions', '*', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Audit permissions
INSERT INTO permissions (id, permission_key, description, resource, action, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'audit:read', 'View audit logs', 'audit', 'read', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'audit:export', 'Export audit logs', 'audit', 'export', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign permissions to SUPER_ADMIN role (all permissions)
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'SUPER_ADMIN';

-- Assign permissions to ORG_OWNER role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ORG_OWNER'
AND p.permission_key IN (
    'orgs:*',
    'users:read', 'users:update',
    'roles:*',
    'permissions:read', 'permissions:assign',
    'audit:read', 'audit:export'
);

-- Assign permissions to ORG_ADMIN role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ORG_ADMIN'
AND p.permission_key IN (
    'orgs:read', 'orgs:update', 'orgs:invite',
    'users:read', 'users:update',
    'roles:read', 'roles:assign',
    'permissions:read',
    'audit:read'
);

-- Assign permissions to ORG_MEMBER role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ORG_MEMBER'
AND p.permission_key IN (
    'orgs:read',
    'users:read',
    'roles:read',
    'permissions:read'
);

-- Assign permissions to ORG_VIEWER role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ORG_VIEWER'
AND p.permission_key IN (
    'orgs:read',
    'users:read'
);
