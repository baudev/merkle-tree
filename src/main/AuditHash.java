/**
 * Contains the hash and the left/right position of the hash
 */
public class AuditHash {

    protected byte[] value;
    protected AuditHashTypeEnum nodeType;

    public AuditHash(byte[] value, AuditHashTypeEnum nodeType) {
        this.value = value;
        this.nodeType = nodeType;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public AuditHashTypeEnum getNodeType() {
        return nodeType;
    }

    public void setNodeType(AuditHashTypeEnum nodeType) {
        this.nodeType = nodeType;
    }
}