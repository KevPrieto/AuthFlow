-- AccessFlow Initial Schema Migration
-- Creates all core tables for authentication, authorization, and audit logging

-- Users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_status ON users(status);

-- Organizations table
CREATE TABLE organizations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    plan VARCHAR(20) NOT NULL,
    slug VARCHAR(100) UNIQUE,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_org_name ON organizations(name);
CREATE INDEX idx_org_slug ON organizations(slug);

-- Roles table
CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_system_role BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_role_name ON roles(name);

-- Permissions table
CREATE TABLE permissions (
    id UUID PRIMARY KEY,
    permission_key VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    resource VARCHAR(100),
    action VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_permission_key ON permissions(permission_key);
CREATE INDEX idx_permission_resource ON permissions(resource);

-- Role-Permission mapping table (many-to-many)
CREATE TABLE role_permissions (
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

CREATE INDEX idx_role_perm_role ON role_permissions(role_id);
CREATE INDEX idx_role_perm_permission ON role_permissions(permission_id);

-- Memberships table (User-Organization-Role relationship)
CREATE TABLE memberships (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_org UNIQUE (user_id, organization_id)
);

CREATE INDEX idx_membership_user ON memberships(user_id);
CREATE INDEX idx_membership_org ON memberships(organization_id);
CREATE INDEX idx_membership_role ON memberships(role_id);

-- Sessions table
CREATE TABLE sessions (
    id UUID PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    is_revoked BOOLEAN NOT NULL DEFAULT false,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_session_token ON sessions(token);
CREATE INDEX idx_session_user ON sessions(user_id);
CREATE INDEX idx_session_expiry ON sessions(expires_at);

-- Audit Logs table
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    actor_id UUID NOT NULL,
    actor_email VARCHAR(255) NOT NULL,
    action VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    resource_id UUID,
    organization_id UUID,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    details VARCHAR(2000),
    status VARCHAR(20) NOT NULL
);

CREATE INDEX idx_audit_actor ON audit_logs(actor_id);
CREATE INDEX idx_audit_action ON audit_logs(action);
CREATE INDEX idx_audit_timestamp ON audit_logs(timestamp);
CREATE INDEX idx_audit_resource ON audit_logs(resource_type, resource_id);
CREATE INDEX idx_audit_org ON audit_logs(organization_id);

-- Comments for documentation
COMMENT ON TABLE users IS 'User accounts with authentication credentials';
COMMENT ON TABLE organizations IS 'Multi-tenant organizations';
COMMENT ON TABLE roles IS 'Named sets of permissions for RBAC';
COMMENT ON TABLE permissions IS 'Granular access rights';
COMMENT ON TABLE memberships IS 'User-Organization-Role relationships';
COMMENT ON TABLE sessions IS 'Active authentication sessions with JWT tokens';
COMMENT ON TABLE audit_logs IS 'Security audit trail for compliance';
