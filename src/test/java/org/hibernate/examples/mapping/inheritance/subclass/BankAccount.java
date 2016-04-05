package org.hibernate.examples.mapping.inheritance.subclass;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * org.hibernate.examples.mapping.inheritance.subclass.BankAccount
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 30. 오후 2:13
 */
@Entity(name = "Subclass_BankAccount")
@DiscriminatorValue(value = "BankAccount")
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class BankAccount extends AbstractBilling {

    private String account;
    private String bankname;
    private String swift;

    @Override
    public int hashCode() {
        return HashTool.compute(super.hashCode(), account, bankname);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("account", account)
                    .add("bankname", bankname)
                    .add("swift", swift);
    }

    private static final long serialVersionUID = -6626110047707984345L;

    public String getAccount() {
        return this.account;
    }

    public String getBankname() {
        return this.bankname;
    }

    public String getSwift() {
        return this.swift;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
