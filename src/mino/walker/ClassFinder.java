package mino.walker;

import mino.exception.InterpreterException;
import mino.language_mino.*;
import mino.structure.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Lam on 31/03/2017.
 */
public class ClassFinder
        extends Walker{

    public ClassTable classTable;

    private ClassInfo currentClassInfo;

    private Map<NId, NClassName> idList;

    private Token operatorToken;

    public static void find(
            Node node,
            ClassTable classTable){

        new ClassFinder(classTable).visit(node);
    }

    private ClassFinder(
            ClassTable classTable){

        this.classTable = classTable;
    }

    public void visit(
            Node tree){

        tree.apply(this);
    }

    @Override
    public void caseFile(
            NFile node) {

        visit(node.get_Classdefs());
    }

    @Override
    public void inClassdef(
            NClassdef node) {

        this.currentClassInfo = this.classTable.add(node);
    }

    @Override
    public void outClassdef(
            NClassdef node) {

        this.currentClassInfo = null;
    }

    @Override
    public void caseMember_Field(
            NMember_Field node) {

        this.currentClassInfo.getFieldTable().add(node);
    }

    @Override
    public void caseMember_Method(
            NMember_Method node) {

        Map<NId, NClassName> params = getParams(node.get_IdListOpt());

        NClassName returnParam = null;

        if(node.get_ReturnOpt() instanceof NReturnOpt_One){
            returnParam = ((NReturnOpt_One)node.get_ReturnOpt()).get_ClassName();
        }
        this.currentClassInfo.getMethodTable().add(node, params, returnParam);
    }

    @Override
    public void caseMember_Operator(
            NMember_Operator node) {

        Map<NId, NClassName> params = getParams(node.get_IdListOpt());
        Token operatorToken = getOperatorToken(node.get_Operator());
        NClassName returnParam = null;

        if(node.get_ReturnOpt() instanceof NReturnOpt_One){
            returnParam = ((NReturnOpt_One)node.get_ReturnOpt()).get_ClassName();
        }
        this.currentClassInfo.getMethodTable().add(node, params, returnParam, operatorToken);
    }

    @Override
    public void caseMember_PrimitiveMethod(
            NMember_PrimitiveMethod node) {

        Map<NId, NClassName> params = getParams(node.get_IdListOpt());
        NClassName returnParam = null;

        if(node.get_ReturnOpt() instanceof NReturnOpt_One){
            returnParam = ((NReturnOpt_One)node.get_ReturnOpt()).get_ClassName();
        }
        this.currentClassInfo.getMethodTable().add(node, params, returnParam);
    }

    @Override
    public void caseMember_PrimitiveOperator(
            NMember_PrimitiveOperator node) {

        Map<NId, NClassName> params = getParams(node.get_IdListOpt());
        Token operatorToken = getOperatorToken(node.get_Operator());
        NClassName returnParam = null;

        if(node.get_ReturnOpt() instanceof NReturnOpt_One){
            returnParam = ((NReturnOpt_One)node.get_ReturnOpt()).get_ClassName();
        }
        this.currentClassInfo.getMethodTable().add(node, params, returnParam, operatorToken);
    }

    @Override
    public void inIdList(
            NIdList node) {

        this.idList.put(node.get_Id(), node.get_ClassName());
    }

    @Override
    public void caseAdditionalId(
            NAdditionalId node) {

        this.idList.put(node.get_Id(), node.get_ClassName());
    }

    @Override
    public void caseOperator_Plus(
            NOperator_Plus node) {

        this.operatorToken = node.get_Plus();
    }

    @Override
    public void caseOperator_Min(
            NOperator_Min node) {
        this.operatorToken = node.get_Min();
    }

    @Override
    public void caseOperator_Div(
            NOperator_Div node) {
        this.operatorToken = node.get_Div();
    }

    @Override
    public void caseOperator_Modul(
            NOperator_Modul node) {
        this.operatorToken = node.get_Modul();
    }

    @Override
    public void caseOperator_Mult(
            NOperator_Mult node) {
        this.operatorToken = node.get_Mult();
    }

    @Override
    public void caseOperator_Eq(
            NOperator_Eq node) {

        this.operatorToken = node.get_Eq();
    }

    @Override
    public void caseOperator_NotEq(
            NOperator_NotEq node) {

        this.operatorToken = node.get_NotEq();
    }

    @Override
    public void caseOperator_GreaterThanEqual(
            NOperator_GreaterThanEqual node) {

        this.operatorToken = node.get_Gte();
    }

    @Override
    public void caseOperator_GreaterThan(
            NOperator_GreaterThan node) {

        this.operatorToken = node.get_Gt();
    }

    @Override
    public void caseOperator_LowerThan(
            NOperator_LowerThan node) {

        this.operatorToken = node.get_Lt();
    }

    @Override
    public void caseOperator_LowerThanEqual(
            NOperator_LowerThanEqual node) {

        this.operatorToken = node.get_Lte();
    }

    private Map<NId, NClassName> getParams(
            NIdListOpt node) {

        this.idList = new LinkedHashMap<>();
        visit(node);
        Map<NId, NClassName> idList = this.idList;
        this.idList = null;
        return idList;
    }

    private Token getOperatorToken(
            NOperator node) {

        visit(node);
        Token operatorToken = this.operatorToken;
        this.operatorToken = null;
        return operatorToken;
    }

}
